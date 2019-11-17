package com.example.courtcase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class PaymentActivity extends AppCompatActivity {
    private static final String TAG = "PaymentActivity";
    private FirebaseUser firebaseUser;
    private FirebaseAuth mAuth;
    private String UID ;
    DatabaseReference rootRef;
    String selectedCase,payStus;


    EditText  cardNumberr, namecardd,ccv,month,year;
    String  cardNumberrr, namecarddr,ccvr,monthr,yearr;
    Button submit;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        UID = firebaseUser.getUid();
        selectedCase = getIntent().getExtras().get("selectedCase").toString();
        rootRef = FirebaseDatabase.getInstance().getReference();

        submit = findViewById(R.id.submit);
        cardNumberr = findViewById(R.id.cardNumberr);
        namecardd = findViewById(R.id.namecardd);
        ccv = findViewById(R.id.ccv);
        month = findViewById(R.id.month);
        year = findViewById(R.id.year);
        progressBar = findViewById(R.id.progressBar);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitt();
            }
        });
        rootRef.child("client").child(UID).child("myCase").child(selectedCase).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("paymentStatus").exists()){
                    payStus = dataSnapshot.child("paymentStatus").getValue().toString();
                    Log.e(TAG, "onDataChange: "+payStus );
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public  void submitt() {
        progressBar.setVisibility(View.VISIBLE);
        cardNumberrr = cardNumberr.getText().toString();
        namecarddr = namecardd.getText().toString();
        ccvr = ccv.getText().toString();
        monthr = month.getText().toString();
        yearr = year.getText().toString();


        if (cardNumberrr.isEmpty()) {
            cardNumberr.setError("card number is required");
            cardNumberr.requestFocus();
            return;
        }

        if (namecarddr.isEmpty()) {
            namecardd.setError("name  is required");
            namecardd.requestFocus();
            return;
        }

        if (ccvr.isEmpty()) {
            ccv.setError("cvv  is required");
            ccv.requestFocus();
            return;
        }

        if (monthr.isEmpty()) {
            month.setError("month  is required");
            month.requestFocus();
            return;
        }

        if (yearr.isEmpty()) {
            year.setError("year  is required");
            year.requestFocus();
            return;
        }
        String paymentStatus = "paid";
        HashMap<String,Object> paymentmap = new HashMap<>();
        paymentmap.put("paymentStatus",paymentStatus);
        rootRef.child("client").child(UID).child("myCase").child(selectedCase).updateChildren(paymentmap);
        Toast.makeText(PaymentActivity.this, "Paid Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(PaymentActivity.this,DashBoardActivity.class));

    }
}
