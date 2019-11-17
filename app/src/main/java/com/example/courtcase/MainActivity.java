package com.example.courtcase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

public class MainActivity extends AppCompatActivity {

    EditText emailInput, passwordInput;
    Button singInBtn;
    FirebaseAuth mAuth;
    RadioButton client, lawyer, courtOffice;
    FirebaseUser firebaseUser;
    String UID;
    DatabaseReference rootRef;
    ProgressBar progress;

    CheckBox remeber_me_chk;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference();
        progress = findViewById(R.id.progress);
        String user = mAuth.getCurrentUser().toString();
//        if (!user.equals("")) {
//            //check the user id is in lowyer or client
//            Intent intent = new Intent(MainActivity.this, DashboardOfLowyerActivity.class);
//            startActivity(intent);
//        }
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        remeber_me_chk = findViewById(R.id.remeber_me_chk);
        emailInput = findViewById(R.id.emailSingIn);
        passwordInput = findViewById(R.id.passwordSingIn);
        singInBtn = findViewById(R.id.signInBtn);
        client = findViewById(R.id.radioClientSignIn);
        lawyer = findViewById(R.id.radioLawyerSingIn);
        courtOffice = findViewById(R.id.radioCourtOfficeSignIn);
        singInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signingIn();
            }
        });
        Button newAccountBtn = findViewById(R.id.newAccountBtn);
        newAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, signin.class);
                startActivity(intent);
            }
        });

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            emailInput.setText(loginPreferences.getString("username", ""));
            passwordInput.setText(loginPreferences.getString("password", ""));
            remeber_me_chk.setChecked(true);
        }

    }

    public void signingIn() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty()) {
            emailInput.setError("Email is required");
            emailInput.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Please enter a valid email");
            emailInput.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passwordInput.setError("Password is required");
            passwordInput.requestFocus();
            return;
        }

        if (password.length() < 6) {
            passwordInput.setError("Minimum lenght of password should be 6");
            passwordInput.requestFocus();
            return;
        }

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(emailInput.getWindowToken(), 0);

        email = emailInput.getText().toString();
        password = passwordInput.getText().toString();

        if (remeber_me_chk.isChecked() ) {
            loginPrefsEditor.putBoolean("saveLogin", true);
            loginPrefsEditor.putString("username", email);
            loginPrefsEditor.putString("password", password);


            loginPrefsEditor.commit();
        } else {
            loginPrefsEditor.clear();
            loginPrefsEditor.commit();
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    progress.setVisibility(View.VISIBLE);

                    //get uid
                    UID = firebaseUser.getUid();

                    if (client.isChecked()) {
                        //check for uid exist under child client

                        rootRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.child("client").child(UID).exists()) {
                                    Intent intent = new Intent(MainActivity.this, DashBoardActivity.class);
                                    progress.setVisibility(View.GONE);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                } else {
                                    emailInput.setError("Not a client account");
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    } else if (lawyer.isChecked()) {

                        rootRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.child("lawyer").child(UID).exists()) {
                                    progress.setVisibility(View.GONE);
                                    Intent intent = new Intent(MainActivity.this, DashboardOfLowyerActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                } else {
                                    emailInput.setError("Not a lawyer account");
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

//                        Intent intent = new Intent(MainActivity.this, DashboardOfLowyerActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
                    } else if (courtOffice.isChecked()) {

                        rootRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.child("admin").child(UID).exists()) {
                                    progress.setVisibility(View.GONE);
                                    Intent intent = new Intent(MainActivity.this, AdmincasesListViewActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                } else {
                                    emailInput.setError("Not a courtOffice account");
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


//                        Intent intent = new Intent(MainActivity.this, YourCasesActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }




}


