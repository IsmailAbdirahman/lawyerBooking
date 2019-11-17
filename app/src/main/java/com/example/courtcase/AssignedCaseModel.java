package com.example.courtcase;

public class AssignedCaseModel {

    String  caseName,caseNumber,caseType,clientName;

    public AssignedCaseModel() {
    }


    public AssignedCaseModel(String caseName, String caseNumber, String caseType,String clientName) {
        this.caseName = caseName;
        this.caseNumber = caseNumber;
        this.caseType = caseType;
        this.clientName = clientName;
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

    public  String getclientName(){
        return  clientName;
    }

    public void setclientName(String clientName){
        this.clientName = clientName;
    }




}
