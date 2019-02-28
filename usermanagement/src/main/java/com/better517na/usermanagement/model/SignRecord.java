package com.better517na.usermanagement.model;

public class SignRecord {
    private String stuId;
    private String stuNumber;
    private String stuName;
    private String setId;
    private String setName;
    private String sigTime;

    public String getSetId() {
        return setId;
    }

    public void setSetId(String setId) {
        this.setId = setId;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getStuNumber() {
        return stuNumber;
    }

    public void setStuNumber(String stuNumber) {
        this.stuNumber = stuNumber;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String claName) {
        this.setName = claName;
    }

    public String getSigTime() {
        return sigTime;
    }

    public void setSigTime(String sigTime) {
        this.sigTime = sigTime;
    }
}
