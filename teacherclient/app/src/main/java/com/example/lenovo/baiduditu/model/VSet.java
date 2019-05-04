package com.example.lenovo.baiduditu.model;

import java.io.Serializable;

public class VSet implements Serializable {
    private String claId;
    private String claName;
    private String setId;
    private String setName;
    private String longitude;
    private String latitude;
    private String scope;
    private String startSigTime;
    private String endSigTime;
    private String createrName;
    private String createrID;

    @Override
    public String toString() {
        return "VSet{" +
                "claId='" + claId + '\'' +
                ", claName='" + claName + '\'' +
                ", setName='" + setName + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", scope='" + scope + '\'' +
                ", startSigTime='" + startSigTime + '\'' +
                ", endSigTime='" + endSigTime + '\'' +
                ", createrName='" + createrName + '\'' +
                '}';
    }

    public String getClaId() {
        return claId;
    }

    public void setClaId(String claId) {
        this.claId = claId;
    }

    public String getClaName() {
        return claName;
    }

    public void setClaName(String claName) {
        this.claName = claName;
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
        if(startSigTime.length()>20){
            return startSigTime.substring(0,19);
        }else {
            return startSigTime;
        }
    }

    public void setStartSigTime(String startSigTime) {
        this.startSigTime = startSigTime;
    }

    public String getEndSigTime() {
        if(endSigTime.length()>20){
            return endSigTime.substring(0,19);
        }else {
            return endSigTime;
        }
    }

    public void setEndSigTime(String endSigTime) {
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
