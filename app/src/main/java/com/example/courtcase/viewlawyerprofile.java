package com.example.courtcase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class viewlawyerprofile extends AppCompatActivity {
   static String selectedProfileLawyerId;
    DatabaseReference lawyerInfo;
    TextView nameu,phone,address,age,experience,caseHandled,successrate,fee,email;
    String nameuu,phonee,addresss,agee,experiencee,caseHandledd,successratee,feee,image,emaile;
    ImageView profile_image;
    Button tellCaseButton,backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewlawyerprofile);
        selectedProfileLawyerId = getIntent().getExtras().get("selectedProfile").toString();
        lawyerInfo = FirebaseDatabase.getInstance().getReference().child("lawyer");
        profile_image = findViewById(R.id.profile_image);
        nameu = findViewById(R.id.nameu);
        phone = findViewById(R.id.phonenumr);
        age = findViewById(R.id.ageu);
        address = findViewById(R.id.adrrs);
        email = findViewById(R.id.emailfd);
        experience = findViewById(R.id.expr);
        caseHandled = findViewById(R.id.casehad);
        successrate = findViewById(R.id.suss);
        fee = findViewById(R.id.fees);

        lawyerInfo.child(selectedProfileLawyerId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                emaile = dataSnapshot.child("email").getValue().toString();
                email.setText(emaile);
                nameuu = dataSnapshot.child("name").getValue().toString();
                phonee = dataSnapshot.child("phone").getValue().toString();
                addresss = dataSnapshot.child("address").getValue().toString();
                agee = dataSnapshot.child("age").getValue().toString();
                experiencee = dataSnapshot.child("experience").getValue().toString();
                caseHandledd = dataSnapshot.child("caseHandled").getValue().toString();
                successratee = dataSnapshot.child("succuesRate").getValue().toString();
                feee = dataSnapshot.child("fee").getValue().toString();

                if (dataSnapshot.child("image").exists()) {
                    image = dataSnapshot.child("image").getValue().toString();
                    Picasso.get().load(image).into(profile_image);
                }

                nameu.setText(nameuu);
                phone.setText(phonee);
                address.setText(addresss);
                age.setText(agee);
                experience.setText(experiencee);
                caseHandled.setText(caseHandledd);
                successrate.setText(successratee);
                fee.setText(feee);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        tellCaseButton = findViewById(R.id.tellCaseButton);
        backButton = findViewById(R.id.backButton);
        tellCaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(viewlawyerprofile.this,addcase.class));
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(viewlawyerprofile.this,LawyerListProfileActivity.class));
            }
        });

    }




}
