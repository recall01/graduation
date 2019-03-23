package com.example.lenovo.baiduditu.myClass;

/**
 * Created by lenovo on 2018/1/22.
 */

public class News {
    private String new_id;
    private String new_type;
    private String new_title;
    private String new_content;
    private String new_pubtime;
    private String new_endtime;
    private String new_author;
    public News(){
    }
    public News(String id,String type,String title,String content,String pubtime,String endtime,String author){
        this.new_id=id;
        this.new_type = type;
        this.new_title = title;
        this.new_content = content;
        this.new_pubtime = pubtime;
        this.new_endtime = endtime;
        this.new_author = author;
    }
    public void setId(String id){
        this.new_id=id;
    }
    public void setType(String type){
        this.new_type = type;
    }
    public void setTitle(String title){
        this.new_title = title;
    }
    public void setContent(String content){
        this.new_content = content;
    }
    public void setPubtime(String pubtime){
        this.new_pubtime = pubtime;
    }
    public void setEndtime(String endtime){
        this.new_endtime = endtime;
    }
    public void setAuthor(String author){
        this.new_author = author;
    }
    public String getId(){
        return new_id;
    }
    public String getType(){
        return new_type;
    }
    public String getTitle(){
        return  new_title;
    }
    public String getContent(){
        return new_content;
    }
    public String getPubtime(){
        return new_pubtime;
    }
    public String getEndtime(){
        return new_endtime;
    }
    public String getAuthor(){
        return new_author;
    }
}
