package com.example.courtcase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
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
import java.util.Timer;
import java.util.TimerTask;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class viewcase extends AppCompatActivity {
    private static final String TAG = "viewcase";
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    DatabaseReference rootRef;
    String UID ;
    String selectedCaseProfileId;
    String caseNamee,caseNumbere,caseTypee,caseDescriptione,lastHearingDateInLawyerViewe,nextHearingDatee,uri,sts;
    TextView caseName,caseNumber,caseType,caseDescription,lastHearingDateInLawyerView,nextHearingDate;
    Button acceptButton,rejectButton,downloadDoc;
    String caseAuthorr ;
    String status = "pending";
    ProgressBar downloading;
    DownloadManager downloadmanager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcase);

//        if (status.equals("pending")){
//            acceptButton.setVisibility(View.VISIBLE);
//            rejectButton.setVisibility(View.VISIBLE);
//
//        }else{
//            acceptButton.setVisibility(View.INVISIBLE);
//            rejectButton.setVisibility(View.INVISIBLE);
//        }
        downloading = findViewById(R.id.downloading);
        rootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        UID = firebaseUser.getUid();

        caseName = findViewById(R.id.caseName);
        caseNumber = findViewById(R.id.caseNumber);
        caseType = findViewById(R.id.caseType);
        caseDescription = findViewById(R.id.caseDescription);
        lastHearingDateInLawyerView = findViewById(R.id.lastHearingDateInLawyerView);
        nextHearingDate = findViewById(R.id.nextHearingDateInLawyerView);

        downloadDoc = findViewById(R.id.downloadDoc);


        acceptButton = findViewById(R.id.acceptButton);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptCase();

            }
        });


        rejectButton = findViewById(R.id.rejectButton);
        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rejectCase();

            }
        });

         //  selectedCaseProfileId : is the case ID which the lawyer selected and can accept or reject
        selectedCaseProfileId = getIntent().getExtras().get("selectedCaseProfile").toString();

        //   To get case Author First we have to get the case ID, then get the case Author.
        rootRef.child("cases").child(selectedCaseProfileId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                caseAuthorr =dataSnapshot.child("caseAuthor").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // selectedCaseProfileId we stored the the selected ID/Position.

        rootRef.child("lawyer").child(UID).child("yourAssignedCase").child(selectedCaseProfileId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                caseNamee = dataSnapshot.child("caseName").getValue().toString();
                caseName.setText(caseNamee);
                caseNumbere = dataSnapshot.child("caseNumber").getValue().toString();
                caseNumber.setText(caseNumbere);
                caseTypee = dataSnapshot.child("caseType").getValue().toString();
                caseType.setText(caseTypee);
                caseDescriptione = dataSnapshot.child("caseDescription").getValue().toString();
                caseDescription.setText(caseDescriptione);
                lastHearingDateInLawyerViewe = dataSnapshot.child("lastHearingDate").getValue().toString();
                lastHearingDateInLawyerView.setText(lastHearingDateInLawyerViewe);
                nextHearingDatee = dataSnapshot.child("nextHearingDate").getValue().toString();
                nextHearingDate.setText(nextHearingDatee);

                    if (dataSnapshot.child("document").exists()){
                        uri = dataSnapshot.child("document").getValue().toString();
                    }




                sts = dataSnapshot.child("status").getValue().toString();
                    if (sts.equals("pending")){
                        acceptButton.setVisibility(View.VISIBLE);
                        rejectButton.setVisibility(View.VISIBLE);
                    }
                    else if (sts.equals("Accepted") || sts.equals("Rejected")){
                    acceptButton.setVisibility(View.INVISIBLE);
                    rejectButton.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        downloadDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloading.setVisibility(View.VISIBLE);
                  String url=uri;
                if (url  != null){
                    downloadFile(viewcase.this,"Mobile",".pdf",DIRECTORY_DOWNLOADS,url);
                }else{
                    Toast.makeText(viewcase.this, "not provided any document.", Toast.LENGTH_LONG).show();
                }
                downloading.setVisibility(View.INVISIBLE);
            }
        });
    }


    public void acceptCase(){
         HashMap<String,Object> statusCase = new HashMap<>();
         status ="Accepted";
        statusCase.put("status",status);
         rootRef.child("cases").child(selectedCaseProfileId).updateChildren(statusCase);
         rootRef.child("client").child(caseAuthorr).child("myCase").child(selectedCaseProfileId).updateChildren(statusCase);
         rootRef.child("lawyer").child(UID).child("yourAssignedCase").child(selectedCaseProfileId).updateChildren(statusCase);

    }


    public void rejectCase(){
        HashMap<String,Object> statusCase = new HashMap<>();
         status ="Rejected";
        statusCase.put("status",status);
        rootRef.child("cases").child(selectedCaseProfileId).updateChildren(statusCase);
        rootRef.child("client").child(caseAuthorr).child("myCase").child(selectedCaseProfileId).updateChildren(statusCase);
        rootRef.child("lawyer").child(UID).child("yourAssignedCase").child(selectedCaseProfileId).updateChildren(statusCase);

    }


    public long downloadFile(Context context, String fileName, String fileExtension, String destinationDirectory, String url) {

       // downloading.setVisibility(View.VISIBLE);
         downloadmanager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);

        return downloadmanager.enqueue(request);
    }

}
