package com.example.courtcase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;

public class PickADateActivity extends AppCompatActivity {
    private static final String TAG = "PickADateActivity";
    Button nextHearingDateBtn,lastHearingDateBtn, savebtn;
    TextView nextHearingDatetxt,lastHearingDatetxt;
    private int mYear, mMonth, mDay;
    DatabaseReference rootRef;
    String selectedCaseProfileId;
    HashMap<String,Object> casedataMap = new HashMap<>();
    String lawyerId,casesAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_adate);
        nextHearingDateBtn = findViewById(R.id.nextHearingbtn);
        lastHearingDateBtn = findViewById(R.id.lasttHearingbtn);
        nextHearingDatetxt = findViewById(R.id.nextHearingtx);
        lastHearingDatetxt = findViewById(R.id.lastHearingtx);
        savebtn = findViewById(R.id.savebtn);
        selectedCaseProfileId = getIntent().getExtras().get("selectedCaseProfile").toString();
        rootRef = FirebaseDatabase.getInstance().getReference();
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDate();
            }
        });
        nextHearingDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextHearing();
            }
        });

        lastHearingDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastHearing();
            }
        });

        rootRef.child("cases").child(selectedCaseProfileId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                casesAuthor = dataSnapshot.child("caseAuthor").getValue().toString();
                lawyerId = dataSnapshot.child("lawyerId").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    // Get Current Date
    public void nextHearing(){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                nextHearingDatetxt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    public void lastHearing(){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                lastHearingDatetxt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }



    public  void saveDate(){
        String nextHearingDatee = nextHearingDatetxt.getText().toString();
        String lastHearingDatetxte = lastHearingDatetxt.getText().toString();
        casedataMap.put("nextHearingDate",nextHearingDatee);
        casedataMap.put("lastHearingDate",lastHearingDatetxte);
        rootRef.child("cases").child(selectedCaseProfileId).updateChildren(casedataMap);
        rootRef.child("lawyer").child(lawyerId).child("yourAssignedCase").child(selectedCaseProfileId).updateChildren(casedataMap);
        rootRef.child("client").child(casesAuthor).child("myCase").child(selectedCaseProfileId).updateChildren(casedataMap);
    }


}


