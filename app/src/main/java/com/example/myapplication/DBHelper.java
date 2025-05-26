package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int version=1;
    private static final String dbname="todolist.db";
    public static final String tbname1="user";
    public static final String tbname2="todo";

    public DBHelper(Context context) {
        // 调用父类构造函数，传入默认的数据库名和版本号
        super(context, dbname, null, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+tbname1+" (id INTEGER PRIMARY KEY AUTOINCREMENT,phone TEXT, username TEXT,password TEXT)");
        db.execSQL("CREATE TABLE "+tbname2+" (id INTEGER PRIMARY KEY AUTOINCREMENT, detail TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+tbname1);
        db.execSQL("DROP TABLE IF EXISTS "+tbname2);
        onCreate(db);
    }
}
