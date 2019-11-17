package com.example.courtcase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DashBoardActivity extends AppCompatActivity {

    CardView availableLawyers,yourCases,yourProfile,editProfileDash,logoutClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        availableLawyers = findViewById(R.id.availableLawyers);
        editProfileDash  = findViewById(R.id.editProfileDash);
        availableLawyers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashBoardActivity.this,LawyerListProfileActivity.class));
            }
        });

        yourCases = findViewById(R.id.yourCases);
        yourCases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashBoardActivity.this,YourCasesActivity.class));
            }
        });

        yourProfile = findViewById(R.id.yourProfilee);
        yourProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashBoardActivity.this,viewclientprofile.class));

            }
        });

        editProfileDash = findViewById(R.id.editProfileDash);
        editProfileDash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashBoardActivity.this,EditClientProfileActivity.class));

            }
        });

        logoutClient = findViewById(R.id.logoutClient);
        logoutClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashBoardActivity.this,MainActivity.class));

            }
        });
    }
}
