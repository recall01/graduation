package com.better517na.userdataservice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Sign")
public class Sign {
    @ApiModelProperty(hidden = true)
    private String sigId;
    @ApiModelProperty(value = "学生ID",example = "201812211618")
    private String stuId;
    @ApiModelProperty(hidden = true)
    private String sigTime;
    @ApiModelProperty(value = "签到的经度",example = "90")
    private String longitude;
    @ApiModelProperty(value = "签到的纬度",example = "90")
    private String latitude;
    @ApiModelProperty(value = "签到设置的ID",example = "201812241339")
    private String setId;

    public String getSigId() {
        return sigId;
    }

    public void setSigId(String sigId) {
        this.sigId = sigId;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getSigTime() {
        return sigTime;
    }

    public void setSigTime(String sigTime) {
        this.sigTime = sigTime;
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
}
