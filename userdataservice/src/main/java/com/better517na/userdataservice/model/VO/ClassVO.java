package com.better517na.userdataservice.model.VO;

import com.better517na.userdataservice.model.Class;
import com.better517na.userdataservice.model.Teacher;

public class ClassVO extends Class {
    private Teacher creater;

    public Teacher getCreater() {
        return creater;
    }

    public void setCreater(Teacher creater) {
        this.creater = creater;
    }
}
