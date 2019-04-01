package com.example.lenovo.baiduditu.myClass;

/**
 * Created by lenovo on 2018/1/27.
 */

public class school {
    private String school_id;
    private String school_name;
    public school(){}
    public school(String id,String name){
        this.school_id = id;
        this.school_name = name;
    }
    public void setSchool_id(String id){
        this.school_id = id;
    }
    public void setSchool_name(String name){
        this.school_id = name;
    }
    public String getSchool_id(){
        return school_id;
    }
    public String getSchool_name(){
        return school_name;
    }
}
