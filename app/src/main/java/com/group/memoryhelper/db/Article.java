package com.group.memoryhelper.db;


import android.content.ContentValues;

import java.io.Serializable;

public class Article implements Serializable {
    private int id;
    private String type;
    private String title;
    private String content;
    private String status;
    private String createTime;

    public static ContentValues getValues(Article article){
        ContentValues cv = new ContentValues();
        cv.put("title", article.getTitle());
        cv.put("type", article.getType());
        cv.put("status", article.getStatus());
        cv.put("content", article.getContent());
        cv.put("createTime", article.getCreateTime());
        return cv;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
