package com.example.lenovo.baiduditu.model;

import java.io.Serializable;

public class Set implements Serializable {
    private String setID;
    private String setName;
    private String claID;
    private String longitude;
    private String latitude;
    private String scope;
    private String startSigTime;
    private String endSigTime;
    private String createrID;

    public String getSetID() {
        return setID;
    }

    public void setSetID(String setID) {
        this.setID = setID;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public String getClaID() {
        return claID;
    }

    public void setClaID(String claID) {
        this.claID = claID;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getStartSigTime() {
        return startSigTime;
    }

    public void setStartSigTime(String startSigTime) {
        this.startSigTime = startSigTime;
    }

    public String getEndSigTime() {
        return endSigTime;
    }

    public void setEndSigTime(String endSigTime) {
        this.endSigTime = endSigTime;
    }

    public String getCreaterID() {
        return createrID;
    }

    public void setCreaterID(String createrID) {
        this.createrID = createrID;
    }
}
