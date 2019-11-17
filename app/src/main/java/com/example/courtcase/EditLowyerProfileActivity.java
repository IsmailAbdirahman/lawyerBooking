package com.example.courtcase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class EditLowyerProfileActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    String UID ;
    DatabaseReference rootRef;
    StorageReference mStorage;
    private String downloadImageUrl;
    private  static  final int gallery=5;
    ProgressBar editProgressBari;



    EditText editLawyerName,editLawyerPhone,editLawyerAddress,editLawyerAge,
            editLawyerExpr,editLawyerCaseHandled,editLawyerSuccessRate,editLawyerFee;
    TextView editLawyerEmail;
    ImageView editMyProfileLawyer;
    Button updateLowyerProfile;


    String  editLawyerEmaill,editLawyerNamel,editLawyerPhonel,editLawyerAddressl,editLawyerAgel, editLawyerExprl,
            editLawyerCaseHandledl,editLawyerSuccessRatel,editLawyerFeel,editMyProfileLawyerl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lowyer_profile);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        UID = firebaseUser.getUid();
        rootRef = FirebaseDatabase.getInstance().getReference().child("lawyer");
        updateLowyerProfile = findViewById(R.id.updateLowyerProfile);
        updateLowyerProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateLawyerProfile();
            }
        });
        editMyProfileLawyer = findViewById(R.id.editMyProfileLawyer);
        editMyProfileLawyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,gallery);
            }
        });
        editProgressBari = findViewById(R.id.editProgressBari);
        editLawyerEmail = findViewById(R.id.editLawyerEmail);
        editLawyerName = findViewById(R.id.editLawyerName);
        editLawyerPhone = findViewById(R.id.editLawyerPhone);
        editLawyerAddress = findViewById(R.id.editLawyerAddress);
        editLawyerAge = findViewById(R.id.editLawyerAge);
        editLawyerExpr = findViewById(R.id.editLawyerExpr);
        editLawyerCaseHandled = findViewById(R.id.editLawyerCaseHandled);
        editLawyerSuccessRate = findViewById(R.id.editLawyerSuccessRate);
        editLawyerFee = findViewById(R.id.editLawyerFee);
        mStorage = FirebaseStorage.getInstance().getReference().child("Images");

        rootRef.child(UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                editLawyerEmaill = dataSnapshot.child("email").getValue().toString();
                editLawyerEmail.setText(editLawyerEmaill);
                editLawyerNamel = dataSnapshot.child("name").getValue().toString();
                editLawyerName.setText(editLawyerNamel);
                editLawyerPhonel = dataSnapshot.child("phone").getValue().toString();
                editLawyerPhone.setText(editLawyerPhonel);
                editLawyerAddressl = dataSnapshot.child("address").getValue().toString();
                editLawyerAddress.setText(editLawyerAddressl);
                editLawyerAgel = dataSnapshot.child("age").getValue().toString();
                editLawyerAge.setText(editLawyerAgel);
                editLawyerExprl = dataSnapshot.child("experience").getValue().toString();
                editLawyerExpr.setText(editLawyerExprl);
                editLawyerCaseHandledl = dataSnapshot.child("caseHandled").getValue().toString();
                editLawyerCaseHandled.setText(editLawyerCaseHandledl);
                editLawyerSuccessRatel = dataSnapshot.child("succuesRate").getValue().toString();
                editLawyerSuccessRate.setText(editLawyerSuccessRatel);
                editLawyerFeel = dataSnapshot.child("fee").getValue().toString();
                editLawyerFee.setText(editLawyerFeel);

               editMyProfileLawyerl = dataSnapshot.child("image").getValue().toString();
               Picasso.get().load(editMyProfileLawyerl).into(editMyProfileLawyer);

//                if (dataSnapshot.child("image").exists()){
//                    editMyProfileLawyerl = dataSnapshot.child("image").getValue().toString();
//                    Picasso.get().load(editMyProfileLawyerl).into(editMyProfileLawyer);
//
//                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==gallery){
            Uri uri = data.getData();
            editMyProfileLawyer.setImageURI(uri);
            final StorageReference fileName = mStorage.child("postPhotos/" + uri.getLastPathSegment() + ".png");

            final UploadTask uploadTask = fileName.putFile(uri);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String messge = e.toString();
                    Toast.makeText(EditLowyerProfileActivity.this,"Error "+messge,Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(EditLowyerProfileActivity.this,"Uploaded ",Toast.LENGTH_SHORT).show();
                    Task<Uri> uriTask =uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()){
                                throw   task.getException();
                            }
                            downloadImageUrl = fileName.getDownloadUrl().toString();
                            return  fileName.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            downloadImageUrl = task.getResult().toString();
                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = 100.0 * (taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    System.out.println("Upload is " + progress + "% done");
                    int currentprogress = (int) progress;
                    editProgressBari.setVisibility(View.GONE);
                    editProgressBari.setProgress(currentprogress);
                    if (editProgressBari.getProgress()==currentprogress){
                        editProgressBari.setVisibility(View.VISIBLE);
                    }
                }
            });

        }


    }


    public  void updateLawyerProfile(){
        String  editLawyerNamel,editLawyerPhonel,editLawyerAddressl,editLawyerAgel,
                editLawyerExprl,editLawyerCaseHandledl,editLawyerSuccessRatel,editLawyerFeel;
        editLawyerNamel = editLawyerName.getText().toString();
        editLawyerPhonel = editLawyerPhone.getText().toString();
        editLawyerAddressl = editLawyerAddress.getText().toString();
        editLawyerAgel = editLawyerAge.getText().toString();
        editLawyerExprl = editLawyerExpr.getText().toString();
        editLawyerCaseHandledl = editLawyerCaseHandled.getText().toString();
        editLawyerSuccessRatel = editLawyerSuccessRate.getText().toString();
        editLawyerFeel = editLawyerFee.getText().toString();

        HashMap<String,Object> updateLawyerProfileMap = new HashMap<>();

        updateLawyerProfileMap.put("name",editLawyerNamel);
        updateLawyerProfileMap.put("phone",editLawyerPhonel);
        updateLawyerProfileMap.put("address",editLawyerAddressl);
        updateLawyerProfileMap.put("age",editLawyerAgel);
        updateLawyerProfileMap.put("experience",editLawyerExprl);
        updateLawyerProfileMap.put("caseHandled",editLawyerCaseHandledl);
        updateLawyerProfileMap.put("succuesRate",editLawyerSuccessRatel);
        updateLawyerProfileMap.put("fee",editLawyerFeel);
        updateLawyerProfileMap.put("image",downloadImageUrl);

        rootRef.child(UID).updateChildren(updateLawyerProfileMap);








    }
}
