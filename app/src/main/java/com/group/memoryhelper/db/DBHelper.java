package com.group.memoryhelper.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "learn.db";


    private static final int DATABASE_VERSION = 1;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String user = "create table user ("
                + "id integer primary key autoincrement, "
                + "name VARCHAR, "
                + "password VARCHAR)";
        String type = "create table type ("
                + "id integer primary key autoincrement, "
                + "name VARCHAR)";
        String article = "create table article ("
                + "id integer primary key autoincrement, "
                + "title VARCHAR, "
                + "content VARCHAR, "
                + "type VARCHAR, "
                + "status VARCHAR, "
                + "createTime VARCHAR)";
        String task = "create table task ("
                + "id integer primary key autoincrement, "
                + "articeId VARCHAR, "
                + "time VARCHAR, "
                + "status VARCHAR)";
        db.execSQL(user);
        db.execSQL(type);
        db.execSQL(article);
        db.execSQL(task);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
