package com.example.courtcase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class signin extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference rootRef;
    EditText emailField, passwordField,name,age,phone,address;
   // RadioGroup radioGroup;
    RadioButton client,lawyer;
    Button submit;
    ProgressBar signProgressBari;
    public  String userType = "client";
    static HashMap<String,Object> userdataMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        emailField = findViewById(R.id.emailfield);
        passwordField = findViewById(R.id.pass);
        name = findViewById(R.id.namee);
        age = findViewById(R.id.agee);
        phone = findViewById(R.id.phonenum);
        address = findViewById(R.id.adrs);
        client = findViewById(R.id.radioClient);
        lawyer = findViewById(R.id.radioLawyer);
        signProgressBari = findViewById(R.id.signProgressBari);
        submit = findViewById(R.id.submitReg);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerEmailPassword();
            }
        });


    }


    private void registerEmailPassword() {
        signProgressBari.setVisibility(View.VISIBLE);
        final String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        final String namee = name.getText().toString();
        final  String agee = age.getText().toString();
        final String phonee = phone.getText().toString();
        final String addresss= address.getText().toString();
        if (email.isEmpty()) {
            emailField.setError("Email is required");
            emailField.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailField.setError("Please enter a valid email");
            emailField.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passwordField.setError("Password is required");
            passwordField.requestFocus();
            return;
        }
        if (password.length() < 6) {
            passwordField.setError("Minimum lenght of password should be 6");
            passwordField.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                signProgressBari.setVisibility(View.VISIBLE);
                if (task.isSuccessful()) {
                    finish();
                   // startActivity(new Intent(signin.this, ProfileActivity.class));
                    /// get the UID
                    firebaseUser = mAuth.getCurrentUser();
                    String UID = firebaseUser.getUid();
                    userdataMap.put("name",namee);
                    userdataMap.put("age",agee);
                    userdataMap.put("address",addresss);
                    userdataMap.put("phone",phonee);

                    if (client.isChecked()){
                        userType ="client";
                    }else if (lawyer.isChecked()){

                        userType ="lawyer";
                        Intent intent = new Intent(signin.this,profile.class);
                        startActivity(intent);
                    }
                    rootRef.child(userType).child(UID).setValue(userdataMap);
                    rootRef.child(userType).child(UID).child("email").setValue(email);

                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }



}
