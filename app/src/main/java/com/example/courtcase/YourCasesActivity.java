package com.example.courtcase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class YourCasesActivity extends AppCompatActivity {


    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    String UID ;
    Button paymentBtn;

    private RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<YourCasesIModel> options;
    FirebaseRecyclerAdapter<YourCasesIModel, CasesAdapterViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_cases);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        UID = firebaseUser.getUid();
        recyclerView = findViewById(R.id.receyclerLawyercases);
        paymentBtn = findViewById(R.id.paymentBtn);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("client").child(UID).child("myCase");
        options = new FirebaseRecyclerOptions.Builder<YourCasesIModel>().setQuery(databaseReference, YourCasesIModel.class).build();
        adapter = new FirebaseRecyclerAdapter<YourCasesIModel, CasesAdapterViewHolder>(options) {
            @NonNull
            @Override
            public CasesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cases,parent,false);
                return new CasesAdapterViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull CasesAdapterViewHolder holder, final int position, @NonNull YourCasesIModel model) {
                holder.yourLawyerName.setText("Lawyer name: "+model.getYourLawyerName());
                holder.caseName.setText("case name: "+model.getCaseName());
                holder.caseNumber.setText("case No: "+model.getCaseNumber());
                holder.caseType.setText("case Type: "+ model.getCaseType());
                holder.yourstatus.setText("status: "+model.getStatus());
                holder.lastHearingDateInClientView.setText("last date: "+model.getLastHearingDate());
                holder.nextHearingDateInClientView.setText("next date: "+model.getNextHearingDate());
                Picasso.get().load(model.getYourLawyerProfile()).into(holder.imageView);


                //  model.getStatus() gets the current status and if its accepted it will show the hidden button

                if (model.getStatus().equals("Accepted") && model.getPaymentStatus().equals("notPaid") ){
                    holder.paymentBtn.setVisibility(View.VISIBLE);
                    holder.paymentBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String selectedCaseP = getRef(position).getKey();
                            Intent intent = new Intent(YourCasesActivity.this,PaymentActivity.class);
                            intent.putExtra("selectedCase",selectedCaseP);
                            startActivity(intent);
                        }
                    });
                }

                    if (model.getStatus().equals("Accepted") && model.getPaymentStatus().equals("paid")){
                         holder.paymentBtn.setVisibility(View.INVISIBLE);
                }



            }
        };

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}
