package com.example.lenovo.baiduditu.myClass;

/**
 * Created by lenovo on 2017/12/30.
 */

public class dingdan {
    private String good_name;
    private String order_time;
    private String order_price;
    private String good_id;
    public dingdan(){};
    public dingdan(String gname,String time,String price,String id){
        this.good_name = gname;
        this.order_time = time;
        this.order_price = price;
        this.good_id = id;
    }
    public void setgname(String name){
        this.good_name = name;
    }
    public void settime(String time){
        this.order_time = time;
    }
    public void setprice(String price){
        this.order_price =price;
    }
    public void setid(String id){
        this.good_id =id;
    }


    public String getgname(){
        return good_name;
    }
    public String gettime(){
        return order_time;
    }
    public String getprice(){
        return order_price;
    }
    public String getid(){
        return good_id;
    }
}
