package com.example.courtcase;

public class YourCasesIModel {

    String caseAuthor,caseDescription,caseName,caseNumber,caseType,yourLawyerName,yourLawyerProfile,status,lastHearingDate,nextHearingDate,paymentStatus;

    public YourCasesIModel() {
    }

    public YourCasesIModel(String caseAuthor, String caseDescription, String caseName, String caseNumber, String caseType, String yourLawyerName, String yourLawyerProfile, String status, String lastHearingDate, String nextHearingDate, String paymentStatus) {
        this.caseAuthor = caseAuthor;
        this.caseDescription = caseDescription;
        this.caseName = caseName;
        this.caseNumber = caseNumber;
        this.caseType = caseType;
        this.yourLawyerName = yourLawyerName;
        this.yourLawyerProfile = yourLawyerProfile;
        this.status = status;
        this.lastHearingDate = lastHearingDate;
        this.nextHearingDate = nextHearingDate;
        this.paymentStatus = paymentStatus;
    }

    public String getCaseAuthor() {
        return caseAuthor;
    }

    public void setCaseAuthor(String caseAuthor) {
        this.caseAuthor = caseAuthor;
    }

    public String getCaseDescription() {
        return caseDescription;
    }

    public void setCaseDescription(String caseDescription) {
        this.caseDescription = caseDescription;
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

    public String getYourLawyerName() {
        return yourLawyerName;
    }

    public void setYourLawyerName(String yourLawyerName) {
        this.yourLawyerName = yourLawyerName;
    }

    public String getYourLawyerProfile() {
        return yourLawyerProfile;
    }

    public void setYourLawyerProfile(String yourLawyerProfile) {
        this.yourLawyerProfile = yourLawyerProfile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
