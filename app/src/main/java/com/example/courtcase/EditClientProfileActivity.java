package com.example.courtcase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditClientProfileActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    String UID;
    DatabaseReference rootRef;


    EditText clientNameUpdate,clientPhoneUpdate,clientAddressUpdate,clientAgeUpdate;
    TextView clientNEmailUpdate;
    Button clientUpdateProfileButton;
    String nameView,phoneView,addressView,ageView,image,emailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client_profile);

        clientNEmailUpdate = findViewById(R.id.clientNEmailUpdate);
        clientNameUpdate = findViewById(R.id.clientNameUpdate);
        clientPhoneUpdate = findViewById(R.id.clientPhoneUpdate);
        clientAddressUpdate = findViewById(R.id.clientAddressUpdate);
        clientAgeUpdate = findViewById(R.id.clientAgeUpdate);
        clientUpdateProfileButton = findViewById(R.id.clientUpdateProfileButton);


        //   get current user from firebase.
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        UID = firebaseUser.getUid();
        rootRef = FirebaseDatabase.getInstance().getReference().child("client");

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
                }

                clientNameUpdate.setText(nameView);
                clientPhoneUpdate.setText(phoneView);
                clientAgeUpdate.setText(ageView);
                clientAddressUpdate.setText(addressView);
                clientNEmailUpdate.setText(emailView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        clientUpdateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateClientProfile();
            }
        });
    }



    //  update Button we have
    public void updateClientProfile(){
        String nameView,phoneView,addressView,ageView,image,emailView;
        nameView = clientNameUpdate.getText().toString();
        phoneView = clientPhoneUpdate.getText().toString();
        addressView = clientAddressUpdate.getText().toString();
        ageView = clientAgeUpdate.getText().toString();

        HashMap<String,Object> updateClientProfileMap = new HashMap<>();

        updateClientProfileMap.put("name",nameView);
        updateClientProfileMap.put("phone",phoneView);
        updateClientProfileMap.put("address",addressView);
        updateClientProfileMap.put("age",ageView);
        rootRef.child(UID).updateChildren(updateClientProfileMap);
        Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();

    }





}
