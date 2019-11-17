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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class LawyerListProfileActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<LawyerInfo> options;
    FirebaseRecyclerAdapter<LawyerInfo, AdapterLawyerViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_list_profile);
        recyclerView = findViewById(R.id.receyclerLawyer);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("lawyer");
        options = new FirebaseRecyclerOptions.Builder<LawyerInfo>().setQuery(databaseReference, LawyerInfo.class).build();
        adapter = new FirebaseRecyclerAdapter<LawyerInfo, AdapterLawyerViewHolder>(options) {


            @NonNull
            @Override
            public AdapterLawyerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_lawyer_profile,parent,false);
                return new AdapterLawyerViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final AdapterLawyerViewHolder holder, final int position, @NonNull LawyerInfo model) {

                Picasso.get().load(model.getImage()).into(holder.imageView);
                holder.name.setText("Name: "+model.getName());
                holder.age.setText("Age: "+model.getAge());
                holder.experience.setText("Experience: "+model.getExperience()+" years");
                holder.successRate.setText("Success Rate: %"+model.getSuccuesRate());
                holder.fee.setText("Fee: "+model.getFee()+" rs");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String selectedProfile = getRef(position).getKey();
                        Intent intent = new Intent(LawyerListProfileActivity.this,viewlawyerprofile.class);
                        intent.putExtra("selectedProfile",selectedProfile);
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