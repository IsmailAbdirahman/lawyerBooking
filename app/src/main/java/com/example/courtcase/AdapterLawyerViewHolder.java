package com.example.courtcase;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterLawyerViewHolder extends RecyclerView.ViewHolder {

    public TextView name,age,experience,successRate,fee;
    public ImageView imageView;


    public AdapterLawyerViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageview);
        name = itemView.findViewById(R.id.name);
        age = itemView.findViewById(R.id.age);
        experience = itemView.findViewById(R.id.expr);
        successRate = itemView.findViewById(R.id.successRate);
        fee = itemView.findViewById(R.id.fee);

    }
}
