package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBManager_user {
    private static final String TAG="DBManager_user";
    private final DBHelper dbHelper;
    private final String tbname;

    public DBManager_user(Context context) {
        dbHelper=new DBHelper(context);
        tbname=DBHelper.tbname1;
    }

    //添加单条数据到数据库
    public boolean add(User u){
        // 检查用户是否已存在
        if (findByPhone(u.getPhone()) != null) {
            Log.i(TAG, "User already exists with phone: " + u.getPhone());
            return false;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("phone",u.getPhone());
        values.put("username", u.getUsername());
        values.put("password",u.getPassword());
        db.insert(tbname, null, values);
        db.close();
        return true;
    }
    // 根据手机号删除单条数据
    public void delete(String phone) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(tbname, "phone=?", new String[]{String.valueOf(phone)});
        db.close();
    }
    //更新单条数据
    public void update(User u) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("phone",u.getPhone());
        values.put("username", u.getUsername());
        values.put("password",u.getPassword());
        db.update(tbname, values, "phone=?", new String[]{String.valueOf(u.getPhone())});
        db.close();
    }

    // 根据手机号查询用户
    public User findByPhone(String phone) {
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query(tbname, null, "phone=?", new String[]{phone}, null, null, null);
        User u=null;
        if (cursor != null && cursor.moveToFirst()) {
            u=new User();
            u.setId(cursor.getInt(0));
            u.setPhone(cursor.getString(1));
            u.setUsername(cursor.getString(2));
            u.setPassword(cursor.getString(3));
            cursor.close();
            Log.i(TAG, "Found user with phone: " + phone);
        } else {
            Log.i(TAG, "User not found with phone: " + phone);
        }
        db.close();
        return u;
    }
}
