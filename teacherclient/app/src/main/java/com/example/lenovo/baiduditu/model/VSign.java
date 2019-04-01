package com.example.lenovo.baiduditu.model;

import java.io.Serializable;

public class VSign implements Serializable {
    private String stuId;
    private String stuNumber;
    private String stuName;
    private String setId;
    private String setName;
    private String sigTime;

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

    public String getSetId() {
        return setId;
    }

    public void setSetId(String setId) {
        this.setId = setId;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public String getSigTime() {
        if(sigTime.length()>20){
            return sigTime.substring(0,19);
        }else {
            if(sigTime.contains(".")){
                String[] split = sigTime.split(".");
                return split[0];
            }
            return sigTime;
        }
    }

    public void setSigTime(String sigTime) {
        this.sigTime = sigTime;
    }

    @Override
    public String toString() {
        return "VSign{" +
                "stuId='" + stuId + '\'' +
                ", stuNumber='" + stuNumber + '\'' +
                ", stuName='" + stuName + '\'' +
                ", setId='" + setId + '\'' +
                ", setName='" + setName + '\'' +
                ", sigTime='" + sigTime + '\'' +
                '}';
    }
}
