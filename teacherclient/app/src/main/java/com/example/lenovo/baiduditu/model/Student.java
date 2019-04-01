package com.example.lenovo.baiduditu.model;

import java.io.Serializable;

/**
 * Created by lenovo on 2018/12/22.
 */

public class Student implements Serializable{
    private String stuId;
    private String registerTime;
    private String permissions;
    private String claID;
    private String className;
    private String stuMail;
    private String stuName;
    private String stuNumber;
    private String stuPassword;
    private String stuPhone;
    private int stuSex;

    public String getClaID() {
        return claID;
    }

    public void setClaID(String claId) {
        this.claID = claId;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String stuClass) {
        this.className = stuClass;
    }

    public String getStuMail() {
        return stuMail;
    }

    public void setStuMail(String stuMail) {
        this.stuMail = stuMail;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuNumber() {
        return stuNumber;
    }

    public void setStuNumber(String stuNumber) {
        this.stuNumber = stuNumber;
    }

    public String getStuPassword() {
        return stuPassword;
    }

    public void setStuPassword(String stuPassword) {
        this.stuPassword = stuPassword;
    }

    public String getStuPhone() {
        return stuPhone;
    }

    public void setStuPhone(String stuPhone) {
        this.stuPhone = stuPhone;
    }

    public int getStuSex() {
        return stuSex;
    }

    public void setStuSex(int stuSex) {
        this.stuSex = stuSex;
    }

    @Override
    public String toString() {
        return "Student{" +
                "stuClass='" + className + '\'' +
                ", stuMail='" + stuMail + '\'' +
                ", stuName='" + stuName + '\'' +
                ", stuNumber='" + stuNumber + '\'' +
                ", stuPassword='" + stuPassword + '\'' +
                ", stuPhone='" + stuPhone + '\'' +
                ", stuSex=" + stuSex +
                '}';
    }
}
