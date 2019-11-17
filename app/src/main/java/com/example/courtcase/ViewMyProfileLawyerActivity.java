package com.example.courtcase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ViewMyProfileLawyerActivity extends AppCompatActivity {
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    String UID ;
    DatabaseReference rootRef;

    ImageView imageView;
    TextView viewMYNameLaw,viewMyEmailLaw,viewMyPhoneLaw,viewMyAddressLaw
            ,viewMyAgeLaw,viewMyExpr,viewMyCaseHandled,viewMyChrdfee,viewMYSucc;

    String viewMYNameLaww,viewMyEmailLaww,viewMyPhoneLaww,viewMyAddressLaww
            ,viewMyAgeLaww,viewMyExprw,viewMyCaseHandledw,viewMyChrdfeew,viewMYSuccw,imageVieww;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_profile_lawyer);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        UID = firebaseUser.getUid();
        rootRef = FirebaseDatabase.getInstance().getReference().child("lawyer");
        imageView = findViewById(R.id.viewMyProfileLaw);
        viewMYNameLaw = findViewById(R.id.viewMYNameLaw);
        viewMyEmailLaw = findViewById(R.id.viewMyEmailLaw);
        viewMyPhoneLaw = findViewById(R.id.viewMyPhoneLaw);
        viewMyAddressLaw = findViewById(R.id.viewMyAddressLaw);
        viewMyAgeLaw = findViewById(R.id.viewMyAgeLaw);
        viewMyExpr = findViewById(R.id.viewMyExpLAw);
        viewMyCaseHandled= findViewById(R.id.viewMyCaseHandledLaw);
        viewMyChrdfee = findViewById(R.id.viewMYChrfee);
        viewMYSucc = findViewById(R.id.viewMYChrfee);


        rootRef.child(UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                viewMYNameLaww = dataSnapshot.child("name").getValue().toString();
                viewMYNameLaw.setText(viewMYNameLaww);
                viewMyEmailLaww = dataSnapshot.child("email").getValue().toString();
                viewMyEmailLaw.setText(viewMyEmailLaww);
                viewMyPhoneLaww = dataSnapshot.child("phone").getValue().toString();
                viewMyPhoneLaw.setText(viewMyPhoneLaww);
                viewMyAddressLaww = dataSnapshot.child("address").getValue().toString();
                viewMyAddressLaw.setText(viewMyAddressLaww);
                viewMyAgeLaww = dataSnapshot.child("age").getValue().toString();
                viewMyAgeLaw.setText(viewMyAgeLaww);
                viewMyExprw = dataSnapshot.child("experience").getValue().toString();
                viewMyExpr.setText(viewMyExprw);

                viewMyCaseHandledw = dataSnapshot.child("caseHandled").getValue().toString();
                viewMyCaseHandled.setText(viewMyCaseHandledw);
                viewMyChrdfeew = dataSnapshot.child("fee").toString();
                viewMyChrdfee.setText(viewMyChrdfeew);
                viewMYSuccw = dataSnapshot.child("succuesRate").getValue().toString();
                viewMYSucc.setText(viewMYSuccw);

                if (dataSnapshot.child("image").exists()){
                    imageVieww= dataSnapshot.child("image").getValue().toString();
                    Picasso.get().load(imageVieww).into(imageView);
                }









            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






    }
}
