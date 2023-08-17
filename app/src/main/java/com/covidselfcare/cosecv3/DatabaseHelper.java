package com.covidselfcare.cosecv3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "USER_PERS_REC";
    private static final String TABLE_NAME = "USER_DATA_PERS";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "USERNAME";
    private static final String COL_3 = "GENDER";
    private static final String COL_4 = "AGE";
    private static final String COL_5 = "PASSWORD";
    public static final String  COL_6 = "RISK";
    public static final String  COL_7 = "ANS1";
    public static final String  COL_8 = "ANS2";
    public static final String  COL_9 = "ANS3";
    public static final String COL_10 = "TOKEN";
    public static final String COL_11 = "TIMETAG";
    public static final String COL_12 = "DATETAG";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME , null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT , GENDER TEXT ,AGE TEXT, PASSWORD TEXT,RISK TEXT DEFAULT 0, ANS1 TEXT DEFAULT 'NO',ANS2 TEXT DEFAULT 'NO',ANS3 TEXT DEFAULT 'NO',TOKEN TEXT DEFAULT '',TIMETAG TEXT DEFAULT '',DATETAG TEXT DEFAULT '')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean registerUser(String name,String gender,String age,String password)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(COL_2,name);
        values.put(COL_3,gender);
        values.put(COL_4,age);
        values.put(COL_5,password);


        long result =db.insert(TABLE_NAME, null, values);
        db.close();
        if(result == -1 || name.isEmpty() || gender.isEmpty() || age.isEmpty() || password.isEmpty())
            return false;
        else
            return true;
    }

    public void updateRisk(String user,String Risk,String a1,String a2,String a3){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_6,Risk);
        values.put(COL_7,a1);
        values.put(COL_8,a2);
        values.put(COL_9,a3);
        String[] args = new String[]{user};
        db.update(TABLE_NAME,values,COL_2 + "=?", args);
    }


    public boolean checkUser(String username,String password)
    {
        if(username.isEmpty() || password.isEmpty())
            return false;
        SQLiteDatabase db =this.getWritableDatabase();
        String [] coloumns = { COL_1 };
        String selection = COL_2 + "=?" + " and " + COL_5 +"=?";
        String [] selectionArgs = {username , password};
        Cursor cursor = db.query(TABLE_NAME, coloumns, selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        db.close();
        if(count > 0)
            return true;
        else
            return false;
    }

    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME,null);
        return res;
    }

    public void setToken(String username,String token,String time,String date)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_10,token);
        values.put(COL_11,time);
        values.put(COL_12,date);
        String[] args = new String[]{username};
        db.update(TABLE_NAME,values,COL_2 + "=?", args);
    }

}
