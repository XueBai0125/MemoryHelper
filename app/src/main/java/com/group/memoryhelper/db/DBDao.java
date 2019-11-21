package com.group.memoryhelper.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.group.memoryhelper.TimeUtils;

import java.util.ArrayList;
import java.util.List;


public class DBDao {

    private static DBDao userDB;

    private SQLiteDatabase db;

    private DBDao(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public static DBDao getInstance(Context context) {
        if (userDB == null) {
            userDB = new DBDao(context);
        }
        return userDB;
    }




    public boolean signUp(User user) {
        if (user != null) {
            Cursor cursor = db .query("user", null, "name=?", new String[]{user.getName()}, null, null, null);
            if (cursor.getCount() > 0) {
                return false;
            } else {
                db.insert("user",null,User.getUserValues(user));
                return true;
            }
        }
        return false;

    }



    public User getUserInfoById(int id) {
        User user = null;
        Cursor cursor = db .query("user", null, "id=?",  new String[]{id+""}, null, null, null);
        if(cursor!=null){
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                String name  = cursor.getString(cursor.getColumnIndex("name"));
                String password  = cursor.getString(cursor.getColumnIndex("password"));
                user = new User();
                user.setName(name);
                user.setPassword(password);
                user.setId(id);
            }
            cursor.close();
        }
        return user;
    }

    public int signIn(String name , String password){
        Cursor cursor = db .query("user", null, "name=?"+" and "+"password=?", new String[]{name,password}, null, null, null);
        if (cursor.getCount()>0){
            cursor.moveToNext();
            return cursor.getInt(cursor.getColumnIndex("id"));
        }else {
            return -1;
        }
    }



    //添加类别
    public boolean  insertType(Type type) {
        if (type != null) {
            Cursor cursor = db .query("type", null, "name=?", new String[]{type.getName()}, null, null, null);
            if (cursor.getCount() > 0) {
                return false;
            } else {
                db.insert("type",null, Type.getValues(type));
                return true;
            }
        }
        return false;

    }

    public List<Type> loadTypes(){
        ArrayList<Type> types = new ArrayList<>();
        Cursor cursor = db.query("type", null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                Type type = new Type(id,name);
                types.add(type);
            }
            cursor.close();
        }
        return types;
    }



    public long insertArticle(Article order) {
        long result = -1;
        if (order != null) {
             result = db.insert("article", null, Article.getValues(order));
        }
        return  result;
    }
    public Boolean updateArticle(Article order) {
        if (order != null) {
            db.update("article", Article.getValues(order),"id"+ "= ? ", new String[]{order.getId()+""}) ;

        }
        return  false;
    }
    public List<Article> loadArticleByTopDay(){
        ArrayList<Article> orderList = new ArrayList<Article>();
        Cursor cursor = db.query("article", null, "createTime=?", new String[]{TimeUtils.getCurDay()}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String createTime = cursor.getString(cursor.getColumnIndex("createTime"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String status = cursor.getString(cursor.getColumnIndex("status"));
                String type = cursor.getString(cursor.getColumnIndex("type"));
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                Article article =  new Article();
                article.setType(type);
                article.setId(id);
                article.setTitle(title);
                article.setContent(content);
                article.setStatus(status);
                article.setCreateTime(createTime);
                orderList.add(article);
            }
            cursor.close();
        }
        return orderList;
    }


    public List<Article> loadArticleByType(String type){
        ArrayList<Article> orderList = new ArrayList<Article>();
        Cursor cursor = db.query("article", null, "type=?", new String[]{type}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String createTime = cursor.getString(cursor.getColumnIndex("createTime"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String status = cursor.getString(cursor.getColumnIndex("status"));
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                Article article =  new Article();
                article.setType(type);
                article.setId(id);
                article.setTitle(title);
                article.setContent(content);
                article.setStatus(status);
                article.setCreateTime(createTime);
                orderList.add(article);
            }

            cursor.close();
        }
        return orderList;
    }
    public List<Article> loadArticeByID(String id){
        ArrayList<Article> orderList = new ArrayList<Article>();
        Cursor cursor = db.query("article", null, "id=?", new String[]{id}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String createTime = cursor.getString(cursor.getColumnIndex("createTime"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String status = cursor.getString(cursor.getColumnIndex("status"));
                String type = cursor.getString(cursor.getColumnIndex("type"));
                Article article =  new Article();
                article.setType(type);
                article.setTitle(title);
                article.setContent(content);
                article.setStatus(status);
                article.setCreateTime(createTime);
                orderList.add(article);
            }
            cursor.close();
        }
        return orderList;
    }
    public boolean  insertTask(Task artice) {
        if (artice != null) {
            db.insert("task",null, Task.getValues(artice));
        }
        return false;

    }
    public Boolean updateTask(Task task) {
        if (task != null) {
            db.update("task",Task.getValues(task),"id"+ "= ? ", new String[]{task.getId()+""}) ;

        }
        return  false;
    }
    //获取所有的任务
    public List<Task> loadTask(){
        ArrayList<Task> orderList = new ArrayList<Task>();
        Cursor cursor = db.query("task", null, null,null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String status = cursor.getString(cursor.getColumnIndex("status"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                String articeId = cursor.getString(cursor.getColumnIndex("articeId"));
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                Task artice =  new Task();
                artice.setArticeId(articeId);
                artice.setId(id);
                artice.setTime(time);
                artice.setStatus(status);
                orderList.add(artice);
            }

            cursor.close();
        }
        return orderList;
    }
    public List<Task> loadTaskByToday(){
        ArrayList<Task> orderList = new ArrayList<Task>();
        Cursor cursor = db.query("task", null, "time=?", new String[]{TimeUtils.getCurDay()}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String status = cursor.getString(cursor.getColumnIndex("status"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                String articleId = cursor.getString(cursor.getColumnIndex("articeId"));
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                Task task =  new Task();
                task.setArticeId(articleId);
                task.setId(id);
                task.setTime(time);
                task.setStatus(status);
                orderList.add(task);
            }

            cursor.close();
        }
        return orderList;
    }
}
