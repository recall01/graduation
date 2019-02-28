package com.better517na.usermanagement.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Student")
public class VStudent extends Student{
    @ApiModelProperty(value = "学生所在班级",example = "三年级二班",required = false)
    private String claName;

    public String getClaName() {
        return claName;
    }

    public void setClaName(String claName) {
        this.claName = claName;
    }
}
