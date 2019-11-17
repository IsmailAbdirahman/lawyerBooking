package com.example.courtcase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class viewclientprofile extends AppCompatActivity {
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    String UID;
    DatabaseReference rootRef;


    TextView clientNameView,clientEmailView,clientPhoneView,clientAgeView,clientAddressView;
    ImageView clientImageView;

    String nameView,phoneView,addressView,ageView,image,emailView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewclientprofile);
        clientNameView = findViewById(R.id.clientNameView);
        clientEmailView = findViewById(R.id.clientEmailView);
        clientPhoneView = findViewById(R.id.clientPhoneView);
        clientAgeView = findViewById(R.id.clientAgeView);
        clientAddressView = findViewById(R.id.clientAddressView);

        //   get current user from firebase.
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        UID = firebaseUser.getUid();
        rootRef = FirebaseDatabase.getInstance().getReference().child("client");


        //    we need to get the profile of current loging user from firebase.
        rootRef.child(UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    nameView = dataSnapshot.child("name").getValue().toString();
                    phoneView = dataSnapshot.child("phone").getValue().toString();
                    ageView = dataSnapshot.child("age").getValue().toString();
                    addressView = dataSnapshot.child("address").getValue().toString();
                if (dataSnapshot.child("email").exists()) {
                    emailView = dataSnapshot.child("email").getValue().toString();
                }else{
                    Toast.makeText(viewclientprofile.this, "email not found", Toast.LENGTH_SHORT).show();
                }



                clientNameView.setText(nameView);
                clientPhoneView.setText(phoneView);
                clientAgeView.setText(ageView);
                clientAddressView.setText(addressView);
                clientEmailView.setText(emailView);




            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
