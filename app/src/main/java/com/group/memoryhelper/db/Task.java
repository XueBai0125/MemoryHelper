package com.group.memoryhelper.db;


import android.content.ContentValues;

import java.io.Serializable;

public class Task implements Serializable {
    public int id;
    public String articeId;
    public String time;
    public String status;
    public static ContentValues getValues(Task artice){
        ContentValues cv = new ContentValues();
        cv.put("articeId", artice.getArticeId());
        cv.put("time", artice.getTime());
        cv.put("status", artice.getStatus());
        return cv;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArticeId() {
        return articeId;
    }

    public void setArticeId(String articeId) {
        this.articeId = articeId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
