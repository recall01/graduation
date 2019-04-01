package com.example.lenovo.baiduditu.model;

import java.io.Serializable;

public class Sign implements Serializable {
    private String stuId;
    private String longitude;
    private String latitude;
    private String setId;

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
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

    public String getSetId() {
        return setId;
    }

    public void setSetId(String setId) {
        this.setId = setId;
    }


    @Override
    public String toString() {
        return "Sign{" +
                "stuId='" + stuId + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", setId='" + setId + '\'' +
                '}';
    }
}
