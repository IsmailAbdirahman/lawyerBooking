package com.example.courtcase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;

public class addcase extends AppCompatActivity {
    private static final String TAG = "addcase";
    static HashMap<String,Object> casedataMap = new HashMap<>();

    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    DatabaseReference rootRef;
      String UID ;
    //String yourLawyer;

      String assignedLawyer;
    String nameuu,image;
    String clientName;


    Uri pdfUri;
    FirebaseStorage storage;
    FirebaseDatabase databasee;
    String  url;
    ProgressBar uploadProgressBar;








    //   UI     VARIABLE
    EditText caseName,caseNumber,caseType,caseDescription,lastHearingDatee;
    Button document,upload,submitCase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcase);

        // USER   INFO    FIRE BASE
        rootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        UID = firebaseUser.getUid();

        //UI    INPUT DATA
        caseName = findViewById(R.id.casenam);
        caseNumber = findViewById(R.id.casenum);
        caseType = findViewById(R.id.caseType);
        caseDescription = findViewById(R.id.desp);
       // lastHearingDate = findViewById(R.id.lastHearingDate);
        assignedLawyer = viewlawyerprofile.selectedProfileLawyerId;


        //  -----ADDING A DOCUMENT--------------------

        document = findViewById(R.id.document);
        upload = findViewById(R.id.upload);
        storage = FirebaseStorage.getInstance();
        databasee = FirebaseDatabase.getInstance();
        uploadProgressBar = findViewById(R.id.uploadProgressBar);
        document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPDF();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pdfUri != null){
                  uploadFile(pdfUri);
                }
            }
        });


        //----------------------------------------------------

        submitCase = findViewById(R.id.submitCase);
        submitCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCaseData();
                startActivity(new Intent(addcase.this,DashBoardActivity.class));
            }
        });


        rootRef.child("lawyer").child(assignedLawyer).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                image = dataSnapshot.child("image").getValue().toString();
                nameuu = dataSnapshot.child("name").getValue().toString();
                casedataMap.put("yourLawyerName",nameuu);
                casedataMap.put("yourLawyerProfile",image);
                casedataMap.put("lawyerId",assignedLawyer);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        rootRef.child("client").child(UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clientName= dataSnapshot.child("name").getValue().toString();
                casedataMap.put("clientName",clientName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void selectPDF(){
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==86 && resultCode ==RESULT_OK && data!=null){
                pdfUri = data.getData();
        }else{
            Toast.makeText(this, "select a file please", Toast.LENGTH_SHORT).show();
        }
    }

    public  void uploadFile(Uri pdfUri){
        final String fileName = System.currentTimeMillis()+"";
        StorageReference storageReference = storage.getReference();
        storageReference.child("uploads").child(fileName).putFile(pdfUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        url = uri.toString(); //here u can get your url
                        Log.e(TAG, "onSuccess: "+url );

                    }
                });

                //url=  taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();

            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(addcase.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = 100.0 * (taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                System.out.println("Upload is " + progress + "% done");
                int currentprogress = (int) progress;
                uploadProgressBar.setVisibility(View.GONE);
                uploadProgressBar.setProgress(currentprogress);
                if (uploadProgressBar.getProgress()==currentprogress){
                    uploadProgressBar.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    public void addCaseData(){
        String status ="pending";
        String nextHearingDate ="waiting";
        String lastHearingDaten ="waiting";
        String paymentStatus ="notPaid";
        String caseNamee =caseName.getText().toString();
        String caseNumberr =caseNumber.getText().toString();
        String casetypee =caseType.getText().toString();
        String caseDescriptionn =caseDescription.getText().toString();
       // String lastHearingDaten =lastHearingDate.getText().toString();

         casedataMap.put("caseAuthor",UID);
         casedataMap.put("caseName",caseNamee);
         casedataMap.put("caseNumber",caseNumberr);
         casedataMap.put("caseType",casetypee);
         casedataMap.put("caseDescription",caseDescriptionn);
         casedataMap.put("lastHearingDate",lastHearingDaten);
         casedataMap.put("status",status);
         casedataMap.put("nextHearingDate",nextHearingDate);
         casedataMap.put("paymentStatus",paymentStatus);
         casedataMap.put("document",url);


         rootRef.child("cases").child(caseNumberr).setValue(casedataMap);
         rootRef.child("lawyer").child(assignedLawyer).child("yourAssignedCase").child(caseNumberr).updateChildren(casedataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(addcase.this, "Uploaded", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(addcase.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
         rootRef.child("client").child(UID).child("myCase").child(caseNumberr).updateChildren(casedataMap);

    }
}
