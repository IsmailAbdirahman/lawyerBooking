package com.example.courtcase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DashboardOfLowyerActivity extends AppCompatActivity {
    CardView assignedCases,lowyerProfilee,editLowyerProfile,logoutLowyer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_of_lowyer);

        assignedCases = findViewById(R.id.assignedCases);
        assignedCases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardOfLowyerActivity.this,AssignedCaseListActivity.class));
            }
        });
        lowyerProfilee = findViewById(R.id.lowyerProfilee);
        lowyerProfilee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardOfLowyerActivity.this,ViewMyProfileLawyerActivity.class));

            }
        });
        editLowyerProfile = findViewById(R.id.editLowyerProfile);
        editLowyerProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardOfLowyerActivity.this,EditLowyerProfileActivity.class));

            }
        });
        logoutLowyer = findViewById(R.id.logoutLowyer);
        logoutLowyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardOfLowyerActivity.this,MainActivity.class));

            }
        });


    }
}
