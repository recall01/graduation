package com.better517na.usermanagement.model;

public class VSet {
    private String claID;
    private String claName;
    private String setID;
    private String setName;
    private String longitude;
    private String latitude;
    private String scope;
    private String startSigTime;
    private String endSigTime;
    private String createrName;

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

    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }
}
