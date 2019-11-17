package com.example.courtcase;

public class AdminModel {
    String  caseName,caseNumber,caseType, lastHearingDate,nextHearingDate;

    public AdminModel() {
    }


    public AdminModel(String caseName, String caseNumber, String caseType, String lastHearingDate, String nextHearingDate) {
        this.caseName = caseName;
        this.caseNumber = caseNumber;
        this.caseType = caseType;
        this.lastHearingDate = lastHearingDate;
        this.nextHearingDate = nextHearingDate;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getLastHearingDate() {
        return lastHearingDate;
    }

    public void setLastHearingDate(String lastHearingDate) {
        this.lastHearingDate = lastHearingDate;
    }

    public String getNextHearingDate() {
        return nextHearingDate;
    }

    public void setNextHearingDate(String nextHearingDate) {
        this.nextHearingDate = nextHearingDate;
    }
}
