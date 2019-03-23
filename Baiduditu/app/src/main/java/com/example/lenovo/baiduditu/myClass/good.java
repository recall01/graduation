package com.example.lenovo.baiduditu.myClass;

/**
 * Created by lenovo on 2017/12/17.
 */

public class good {
    public int good_id;
    public String good_name;
    public String tupian_url;
    public String good_num;
    private String up_time;
    public good(int id,String name,String url,String num){
        this.good_id =id;
        this.good_name =name;
        this.tupian_url =url;
        this.good_num =num;
    }
    public void setTime(String time){
        this.up_time =time;
    }
    public String getTime(){
        return up_time;
    }
}
