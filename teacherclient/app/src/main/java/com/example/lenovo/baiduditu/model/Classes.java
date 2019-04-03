package com.example.lenovo.baiduditu.model;

import java.io.Serializable;

/**
 * Created by lenovo on 2018/12/22.
 */

public class Classes implements Serializable{
    protected String claId;
    protected String claName;
    protected String createTime;
    protected String createrId;

    public String getClaId() {
        return claId;
    }

    public void setClaId(String claId) {
        this.claId = claId;
    }

    public String getClaName() {
        return claName;
    }

    public void setClaName(String claName) {
        this.claName = claName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }
}
