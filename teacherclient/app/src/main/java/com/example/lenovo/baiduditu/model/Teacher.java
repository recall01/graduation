package com.example.lenovo.baiduditu.model;

import java.io.Serializable;

/**
 * Created by lenovo on 2018/12/22.
 */

public class Teacher implements Serializable{
    protected String teaId;
    protected String teaNumber;
    protected String teaName;
    protected String teaPhone;
    protected String registerTime;
    protected String permissions;

    public String getTeaId() {
        return teaId;
    }

    public void setTeaId(String teaId) {
        this.teaId = teaId;
    }

    public String getTeaNumber() {
        return teaNumber;
    }

    public void setTeaNumber(String teaNumber) {
        this.teaNumber = teaNumber;
    }

    public String getTeaName() {
        return teaName;
    }

    public void setTeaName(String teaName) {
        this.teaName = teaName;
    }

    public String getTeaPhone() {
        return teaPhone;
    }

    public void setTeaPhone(String teaPhone) {
        this.teaPhone = teaPhone;
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

    @Override
    public String toString() {
        return "Teacher{" +
                "teaId='" + teaId + '\'' +
                ", teaName='" + teaName + '\'' +
                ", teaPhone='" + teaPhone + '\'' +
                ", registerTime='" + registerTime + '\'' +
                ", permissions='" + permissions + '\'' +
                '}';
    }
}
