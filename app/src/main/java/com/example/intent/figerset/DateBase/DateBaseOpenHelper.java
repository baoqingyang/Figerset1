package com.example.intent.figerset.DateBase;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class DateBaseOpenHelper extends SQLiteOpenHelper {

    private Context mContext;
    private String CREATE_USERDATA = "create table Lock(name varchar(200) primary key,pw varchar(10))";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERDATA);
    }

    public DateBaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version) {
        super(context, name, cursorFactory, version);
        mContext = context;
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }







}
