package com.example.lwh.project_school.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lwh.project_school.Activity.Out.Response.ResponseBody;

import java.text.SimpleDateFormat;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "School_db2";
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate    (SQLiteDatabase db) {
        db.execSQL("create table " + "token_table" + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,TOKEN TEXT,EMAIL TEXT)");
        db.execSQL("create table sleep_out_table" + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,ACCEPT INTEGER,IDX INTEGER,START_DATE TEXT" +
                ",END_DATE TEXT,REASON TEXT,CLASS_IDX INTEGER,STUDENT_EMAIL STRING)");
        db.execSQL("create table go_out_table" + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,ACCEPT INTEGER,IDX INTEGER,START_DATE TEXT" +
                ",END_DATE TEXT,REASON TEXT,CLASS_IDX INTEGER,STUDENT_EMAIL STRING)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "token_table");
        db.execSQL("DROP TABLE IF EXISTS go_out_table");
        db.execSQL("DROP TABLE IF EXISTS sleep_out_table");
        onCreate(db);
    }

    public boolean insertData(String TABLE_NAME, String token,String email, ResponseBody responseBody) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        switch (TABLE_NAME) {
            case "token_table":
                contentValues.put("TOKEN", token);
                contentValues.put("EMAIL",email);
                break;
            case "sleep_out_table":
                contentValues.put("ACCEPT", responseBody.getData().getSleep_out().getAccept());
                contentValues.put("IDX", responseBody.getData().getSleep_out().getIdx());
                contentValues.put("START_DATE", format.format(responseBody.getData().getSleep_out().getStart_time()));
                contentValues.put("END_DATE", format.format(responseBody.getData().getSleep_out().getEnd_time()));
                contentValues.put("REASON", responseBody.getData().getSleep_out().getReason());
                contentValues.put("CLASS_IDX", responseBody.getData().getSleep_out().getClass_idx());
                contentValues.put("STUDENT_EMAIL", responseBody.getData().getSleep_out().getStudent_email());
                break;
            case "go_out_table":
                contentValues.put("ACCEPT", responseBody.getData().getGo_out().getAccept());
                contentValues.put("IDX", responseBody.getData().getGo_out().getIdx());
                contentValues.put("START_DATE", format.format(responseBody.getData().getGo_out().getStart_time()));
                contentValues.put("END_DATE", format.format(responseBody.getData().getGo_out().getEnd_time()));
                contentValues.put("REASON", responseBody.getData().getGo_out().getReason());
                contentValues.put("CLASS_IDX", responseBody.getData().getGo_out().getClass_idx());
                contentValues.put("STUDENT_EMAIL", responseBody.getData().getGo_out().getStudent_email());
                break;
        }
        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return result != -1;
    }

    /*public void createTB(String what, String table_name, String command) {// 테이블 만들기 연습용으로 해봄 ㅋ
        SQLiteDatabase db = this.getWritableDatabase();
        /*switch (what){
            case "create":
                db.execSQL("create table"+table_name+command);
                break;
            case "delete":
                break;
            default:
                break;
        }
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,TOKEN TEXT)");

    }*/

    public Cursor getAllData(String TABLE_NAME) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public Cursor selectQuery(String TABLE_NAME, String condition) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where " + condition, null);
        return res;
    }

    public boolean updateData(String TABLE_NAME, String accept, String idx) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ACCEPT", accept);
        db.update(TABLE_NAME, contentValues, "IDX = ?", new String[]{idx});
        return true;
    }

    public Integer deleteData(String id, String TABLE_NAME) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }
    /*public void deleteEverything(String TABLE_NAME){
        Cursor res=getAllData(TABLE_NAME);
        res.moveToFirst();
        do{
            deleteData(Integer.toString(res.getInt(0)),TABLE_NAME);
        }while (res.moveToNext());
        SQLiteDatabase db= this.getWritableDatabase();
    }*/

    public String getToken() {
        Cursor res = getAllData("token_table");
        res.moveToLast();
        return res.getString(1);
    }

    /*public String getData(Integer idx){
        Cursor res=getAllData("out_table4");
        res.moveToLast();
        return res.getString(idx);
    }*/
    public void delete(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + tableName);
    }
    /*public void fasdf(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("create table out_table4" +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,TYPE TEXT,ACCEPT INTEGER,IDX INTEGER,START_DATE TEXT" +
                ",END_DATE TEXT,REASON TEXT,CLASS_IDX INTEGER,STUDENT_EMAIL STRING)");
    }*/
}