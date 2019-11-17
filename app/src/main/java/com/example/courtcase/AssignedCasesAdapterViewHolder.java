package com.example.courtcase;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AssignedCasesAdapterViewHolder extends RecyclerView.ViewHolder {

    TextView  caseName,caseNumber,caseType,clientName;
    public AssignedCasesAdapterViewHolder(@NonNull View itemView) {
        super(itemView);
        caseName = itemView.findViewById(R.id.assignedCaseName);
        caseNumber = itemView.findViewById(R.id.assignedCaseNo);
        caseType  =itemView.findViewById(R.id.assignedCaseType);
        clientName = itemView.findViewById(R.id.clientNameas);

    }
}
