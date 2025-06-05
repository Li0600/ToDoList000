package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class DBManager {
    private DBHelper dbHelper;
    private String tbname;

    public DBManager(Context context) {
        dbHelper=new DBHelper(context);
        tbname=DBHelper.tbname2;
    }

    //添加单条数据到数据库
    public void add(Todo t){
        SQLiteDatabase db = dbHelper.getWritableDatabase();  // 获取可写数据库
        ContentValues values = new ContentValues();           // 存储插入值
        values.put("detail", t.getDetail());            // 键为列名，值为数据
        db.insert(tbname, null, values);  // 插入数据（表名、null列名、值）
        db.close();
    }

<<<<<<< HEAD
=======
//    // 批量添加数据（事务优化版）
//    public void addAll(List<Todo> list) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        db.beginTransaction();  // 开启事务（提升批量插入性能）
//        try {
//            for (Todo t : list) {
//                ContentValues values = new ContentValues();
//                values.put("detail", t.getDetail());
//                db.insert(tbname, null, values);  // 循环插入
//            }
//            db.setTransactionSuccessful();  // 标记事务成功
//        } finally {
//            db.endTransaction();  // 结束事务（无论成功与否）
//            db.close();
//        }
//    }
>>>>>>> 88101029d059b0dc075f7947d1cdef9bb4092e6c
    // 删除表中所有数据
    public void deleteAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(tbname, null, null);  // 条件为null表示删除所有行
        db.close();
    }
    // 根据ID删除单条数据
    public void delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(tbname, "ID=?", new String[]{String.valueOf(id)});
        db.close();
    }
    //更新单条数据
    public void update(Todo t) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("detail", t.getDetail());
        db.update(tbname, values, "ID=?", new String[]{String.valueOf(t.getId())});
        // 参数1：表名；参数2：新值；参数3/4：条件
        db.close();
    }
    // 查询所有数据并返回List
    public List<Todo> listAll() {
        List<Todo> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(tbname, null, null, null, null, null, null
        );
        if (cursor != null) {
            while (cursor.moveToNext()) {  // 遍历游标
                Todo t = new Todo();
                int idIndex = cursor.getColumnIndex("id");
                if (idIndex != -1) {
                    t.setId(cursor.getInt(idIndex));
                }
                int detailIndex = cursor.getColumnIndex("detail");
                if (detailIndex != -1) {
                    t.setDetail(cursor.getString(detailIndex));
                }
                list.add(t);  // 添加到列表
            }
            cursor.close();  // 关闭游标
        }
        db.close();
        return list;
    }
<<<<<<< HEAD
=======
//    // 根据ID查询单条数据
//    public Todo findById(int id) {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.query(tbname, null, "ID=?", new String[]{String.valueOf(id)}, null, null, null
//        );
//        Todo t = null;
//        if (cursor != null && cursor.moveToFirst()) {  // 至少有一条结果
//            t = new Todo();
//            t.setId(cursor.getInt(0));          // 第一列（ID）
//            t.setDetail(cursor.getString(1));   // 第二列（CURNAME）
//            cursor.close();
//        }
//        db.close();
//        return t;
//    }
>>>>>>> 88101029d059b0dc075f7947d1cdef9bb4092e6c





}
