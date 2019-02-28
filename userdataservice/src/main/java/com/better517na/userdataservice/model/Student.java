package com.better517na.userdataservice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Student")
public class Student {
    @ApiModelProperty(hidden = true)
    private String stuId;
    @ApiModelProperty(value = "学生名字",example = "张三丰")
    private String stuName;
    @ApiModelProperty(value = "学生性别,0:女 1:男",example = "1")
    private int stuSex;
    @ApiModelProperty(value = "学生学号,大于4个字符,数字",example = "20181221")
    private String stuNumber;
    @ApiModelProperty(value = "学生密码,大于6个字符",example = "password")
    private String stuPassword;
    @ApiModelProperty(value = "手机号码",example = "18381173671")
    private String stuPhone;
    @ApiModelProperty(value = "邮箱",example = "873718549@qq.com",required = false)
    private String stuMail;
    @ApiModelProperty(value = "学生所在班级编号",example = "201812211050",required = false)
    private String claID;
    @ApiModelProperty(hidden = true)
    private String registerTime;
    @ApiModelProperty(hidden = true)
    private String permissions;

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public int getStuSex() {
        return stuSex;
    }

    public void setStuSex(int stuSex) {
        this.stuSex = stuSex;
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

    public String getStuMail() {
        return stuMail;
    }

    public void setStuMail(String stuMail) {
        this.stuMail = stuMail;
    }

    public String getClaID() {
        return claID;
    }

    public void setClaID(String claID) {
        this.claID = claID;
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
}
