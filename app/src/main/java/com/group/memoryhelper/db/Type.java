package com.group.memoryhelper.db;

import android.content.ContentValues;

import java.io.Serializable;

public class Type implements Serializable {
    int id;
    String name;

    public Type(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Type(String name) {
        this.name = name;
    }

    public Type() {
    }

    public static ContentValues getValues(Type user){
        ContentValues cv = new ContentValues();
        cv.put("name", user.getName());
        return cv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
