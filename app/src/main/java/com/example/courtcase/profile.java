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
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class profile extends AppCompatActivity {
    EditText exp,casehnd,successrate,fee;
    Button submitLowyerBtn;
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    ImageView imageView;
    ProgressBar uploadProgressBari;

    private String downloadImageUrl;
    private  static  final int gallery=5;
    StorageReference mStorage;


    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("lawyer");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        exp = findViewById(R.id.exp);
        mAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference().child("Images");
        casehnd = findViewById(R.id.casehd);
        successrate = findViewById(R.id.susRate);
        fee = findViewById(R.id.fee);

        uploadProgressBari = findViewById(R.id.uploadProgressBari);
        imageView = findViewById(R.id.uploadImage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,gallery);
            }
        });


        submitLowyerBtn = findViewById(R.id.submitLawyerInfo);
        submitLowyerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lawyerData();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==gallery){
            Uri uri = data.getData();
            imageView.setImageURI(uri);
            final StorageReference fileName = mStorage.child("postPhotos/" + uri.getLastPathSegment() + ".png");

            final UploadTask uploadTask = fileName.putFile(uri);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String messge = e.toString();
                    Toast.makeText(profile.this,"Error "+messge,Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(profile.this,"Uploaded ",Toast.LENGTH_SHORT).show();
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
                    uploadProgressBari.setVisibility(View.GONE);
                    uploadProgressBari.setProgress(currentprogress);
                    if (uploadProgressBari.getProgress()==currentprogress){
                        uploadProgressBari.setVisibility(View.VISIBLE);
                    }
                }
            });

        }


    }

    public void lawyerData(){
        firebaseUser = mAuth.getCurrentUser();
        String UID = firebaseUser.getUid();
        String expr = exp.getText().toString();
        String casehdn = casehnd.getText().toString();
        String sucrate = successrate.getText().toString();
        String feee = fee.getText().toString();

        HashMap<String,Object> lawyerdataMap = new HashMap<>();
        //lawyerdataMap = signin.userdataMap;
        lawyerdataMap.put("experience",expr);
        lawyerdataMap.put("caseHandled",casehdn);
        lawyerdataMap.put("succuesRate",sucrate);
        lawyerdataMap.put("fee",feee);
        lawyerdataMap.put("image",downloadImageUrl);
        rootRef.child(UID).updateChildren(lawyerdataMap);
        startActivity(new Intent(profile.this,MainActivity.class));



    }

}
