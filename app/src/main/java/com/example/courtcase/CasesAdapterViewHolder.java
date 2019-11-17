package com.example.courtcase;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CasesAdapterViewHolder  extends RecyclerView.ViewHolder {
    public TextView yourLawyerName,caseName,caseNumber,caseDesription,caseType,yourstatus,lastHearingDateInClientView,nextHearingDateInClientView;
    public ImageView imageView;
    public Button paymentBtn;


    public CasesAdapterViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.yourLawyerProfile);
        yourLawyerName = itemView.findViewById(R.id.lawyerName);
        caseName = itemView.findViewById(R.id.caseName);
        caseNumber = itemView.findViewById(R.id.caseNo);
        caseType = itemView.findViewById(R.id.caseType);
        yourstatus = itemView.findViewById(R.id.status);
        paymentBtn = itemView.findViewById(R.id.paymentBtn);
        lastHearingDateInClientView = itemView.findViewById(R.id.lastHearingDateInClientView);
        nextHearingDateInClientView = itemView.findViewById(R.id.nextHearingDateInClientView);

    }
}
