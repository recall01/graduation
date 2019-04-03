package com.example.lenovo.baiduditu.model.VO;

import com.example.lenovo.baiduditu.model.Classes;
import com.example.lenovo.baiduditu.model.Teacher;

import java.io.Serializable;

/**
 * Created by lenovo on 2018/12/22.
 */

public class TeacherVO extends Teacher{
    protected Classes aClass;

    public Classes getAClass() {
        return aClass;
    }

    public void setAClass(Classes aClass) {
        this.aClass = aClass;
    }
}
