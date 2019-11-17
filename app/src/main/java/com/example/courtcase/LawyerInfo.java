package com.example.courtcase;

public class LawyerInfo {
    String name,age,experience,caseHandled,succuesRate,fee,image;

    public LawyerInfo() {
    }

    public LawyerInfo(String name, String age, String experience, String caseHandled, String succuesRate, String fee, String image) {
        this.name = name;
        this.age = age;
        this.experience = experience;
        this.caseHandled = caseHandled;
        this.succuesRate = succuesRate;
        this.fee = fee;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getCaseHandled() {
        return caseHandled;
    }

    public void setCaseHandled(String caseHandled) {
        this.caseHandled = caseHandled;
    }

    public String getSuccuesRate() {
        return succuesRate;
    }

    public void setSuccuesRate(String succuesRate) {
        this.succuesRate = succuesRate;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
