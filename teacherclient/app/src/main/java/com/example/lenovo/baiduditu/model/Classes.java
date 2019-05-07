package com.example.lenovo.baiduditu.model;

import com.example.lenovo.baiduditu.utils.TimeUtils;

import java.io.Serializable;

/**
 * Created by lenovo on 2018/12/22.
 */

public class Classes implements Serializable{
    protected String claID;
    protected String claName;
    protected String createTime;
    protected String createrID;
    protected String dynamic;

    public String getClaID() {
        return claID;
    }

    public void setClaID(String claID) {
        this.claID = claID;
    }

    public String getClaName() {
        return claName;
    }

    public void setClaName(String claName) {
        this.claName = claName;
    }

    public String getCreateTime() {
        return TimeUtils.handTimeForm(createTime);
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreaterID() {
        return createrID;
    }

    public void setCreaterID(String createrID) {
        this.createrID = createrID;
    }

    public String getDynamic() {
        return dynamic;
    }

    public void setDynamic(String dynamic) {
        this.dynamic = dynamic;
    }
}
