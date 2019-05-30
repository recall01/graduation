package com.better517na.userdataservice.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import java.util.Date;

public class TestSet {
    private String claID;
    private String claName;
    private String setName;
    private String setID;
    private String longitude;
    private String latitude;
    private String scope;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startSigTime;
    @Future(message = "需要一个将来日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endSigTime;
    private String createrName;
    private String createrID;

    public String getSetID() {
        return setID;
    }

    public void setSetID(String setID) {
        this.setID = setID;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

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

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Date getStartSigTime() {
        return startSigTime;
    }

    public void setStartSigTime(Date startSigTime) {
        this.startSigTime = startSigTime;
    }

    public Date getEndSigTime() {
        return endSigTime;
    }

    public void setEndSigTime(Date endSigTime) {
        this.endSigTime = endSigTime;
    }

    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }

    public String getCreaterID() {
        return createrID;
    }

    public void setCreaterID(String createrID) {
        this.createrID = createrID;
    }
}
