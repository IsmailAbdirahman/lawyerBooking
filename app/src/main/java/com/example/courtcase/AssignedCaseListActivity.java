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
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AssignedCaseListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    String UID ;

    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<AssignedCaseModel> options;
    FirebaseRecyclerAdapter<AssignedCaseModel, AssignedCasesAdapterViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigned_case_list);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        UID = firebaseUser.getUid();
        recyclerView = findViewById(R.id.receyclerAssignedCases);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("lawyer").child(UID).child("yourAssignedCase");
        options = new FirebaseRecyclerOptions.Builder<AssignedCaseModel>().setQuery(databaseReference, AssignedCaseModel.class).build();
        adapter = new FirebaseRecyclerAdapter<AssignedCaseModel, AssignedCasesAdapterViewHolder>(options) {

            @NonNull
            @Override
            public AssignedCasesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_assigned_case,parent,false);
                return new AssignedCasesAdapterViewHolder(view);             }

            @Override
            protected void onBindViewHolder(@NonNull AssignedCasesAdapterViewHolder holder, final int position, @NonNull AssignedCaseModel model) {
               holder.caseName.setText("case name: "+model.getCaseName());
               holder.caseNumber.setText("case No: "+model.getCaseNumber());
               holder.caseType.setText("case Typye: "+model.getCaseType());
               holder.clientName.setText("clientName: "+model.getclientName());
               holder.itemView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       String selectedCaseProfile = getRef(position).getKey();
                       Intent intent = new Intent(AssignedCaseListActivity.this,viewcase.class);
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
