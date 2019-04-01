package com.example.lenovo.baiduditu.myClass;

/**
 * Created by lenovo on 2017/12/21.
 */

public class wp_detail {
    private String det_describe;
    private String det_condition;
    private String tupian_url;
    public void setDet_describe(String describe){
        this.det_describe =describe;
    }
    public String getDet_describe(){
        return det_describe;
    }
    public void setDet_condition(String condition){
        this.det_condition =condition;
    }
    public String getDet_condition(){
        return det_condition;
    }
    public void setTupian_url(String url){
        this.tupian_url =url;
    }
    public String getTupian_url(){
        return tupian_url;
    }
}
