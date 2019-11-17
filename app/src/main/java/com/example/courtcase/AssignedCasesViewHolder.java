package com.example.courtcase;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AssignedCasesViewHolder extends RecyclerView.ViewHolder {
    TextView aassignedCaseName,assignedCaseNo,assignedCaseType,clientName;
    public ImageView imageView;





    public AssignedCasesViewHolder(@NonNull View itemView) {
        super(itemView);
      //  imageView =itemView.findViewById(R.id.clientProfile);
        aassignedCaseName = itemView.findViewById(R.id.assignedCaseName);
      //  clientName = itemView.findViewById(R.id.clientNameas);
        assignedCaseNo = itemView.findViewById(R.id.assignedCaseNo);
        assignedCaseType = itemView.findViewById(R.id.assignedCaseType);

    }
}
