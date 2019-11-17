package com.example.courtcase;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdminAdapterViewHolder extends RecyclerView.ViewHolder {

    TextView caseNameInAdminView,caseNoInAdminView,caseTypeInAdminView,
            lastHearingDateInAdminView,nextHearingDateInAdminView;

    public AdminAdapterViewHolder(@NonNull View itemView) {
        super(itemView);

        caseNameInAdminView =itemView.findViewById(R.id.caseNameInAdminView);
        caseNoInAdminView =itemView.findViewById(R.id.caseNoInAdminView);
        caseTypeInAdminView =itemView.findViewById(R.id.caseTypeInAdminView);
        lastHearingDateInAdminView =itemView.findViewById(R.id.lastHearingDateInAdminView);
        nextHearingDateInAdminView =itemView.findViewById(R.id.nextHearingDateInAdminView);

    }
}
