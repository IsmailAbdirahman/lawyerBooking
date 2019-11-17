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
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdmincasesListViewActivity extends AppCompatActivity {
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    String UID ;

    private RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<AdminModel> options;
    FirebaseRecyclerAdapter<AdminModel, AdminAdapterViewHolder> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admincases_list_view);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        UID = firebaseUser.getUid();
        recyclerView = findViewById(R.id.recyclerAdmin);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("cases");
        options = new FirebaseRecyclerOptions.Builder<AdminModel>().setQuery(databaseReference, AdminModel.class).build();
        adapter = new FirebaseRecyclerAdapter<AdminModel, AdminAdapterViewHolder>(options) {


            @NonNull
            @Override
            public AdminAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_admin,parent,false);
                return new AdminAdapterViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull AdminAdapterViewHolder holder, final int position, @NonNull AdminModel model) {
                holder.caseNameInAdminView.setText("case Name: "+model.getCaseName());
                holder.caseNoInAdminView.setText("case Number: "+model.getCaseNumber());
                holder.caseTypeInAdminView.setText("case Type: "+model.getCaseType());
                holder.lastHearingDateInAdminView.setText("last date: "+model.getLastHearingDate());
                holder.nextHearingDateInAdminView.setText("next Date: "+model.getNextHearingDate());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String selectedCaseProfile = getRef(position).getKey();


                        Intent intent = new Intent(AdmincasesListViewActivity.this,PickADateActivity.class);
                        Toast.makeText(AdmincasesListViewActivity.this, ""+selectedCaseProfile, Toast.LENGTH_SHORT).show();
                        intent.putExtra("selectedCaseProfile",selectedCaseProfile);
                        startActivity(intent);
                    }
                });


            }
        };


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}
