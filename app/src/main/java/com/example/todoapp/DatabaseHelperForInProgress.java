package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelperForInProgress extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "TodoIo.db";
    public static final String TABLE_NAME = "InProgressTasks_table";
    public static final String COL_1 = "id";
    public static final String COL_2 = "task";

    public DatabaseHelperForInProgress(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, task TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insert(String task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,task);

        long result= db.insert(TABLE_NAME,null,contentValues);
        if(result == -1){
            return false;
        }else{
            return  true;
        }

    }

    public boolean delete(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        int result= db.delete(TABLE_NAME,"id=?", new String[]{id});
        if(result == -1){
            return false;
        }else{
            return  true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor fetchedData = db.rawQuery(query,null);
        return fetchedData;
    }



}
