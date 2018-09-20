package com.example.intent.figerset.DateBase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

public class DateBaseOperator {
    private DateBaseOpenHelper dateBaseOpenHelper;
    private SQLiteDatabase db;

    public DateBaseOperator(Context context){
        dateBaseOpenHelper = new DateBaseOpenHelper(context,"glock",null,1);
        db = dateBaseOpenHelper.getWritableDatabase();
    }

    public void add(String s){
        ContentValues values = new ContentValues();
        values.put("name","user");
        values.put("pw",s);
        db.insert("Lock",null,values);
    }

    public void update(String s){
        ContentValues values = new ContentValues();
        values.put("pw",s);
        String  whereClause = "name=?";
        String[] whereArgs = {"user"};
        db.update("Lock",values,whereClause,whereArgs);
    }
    public void delete(String name) {
        db.execSQL("delete from Lock where name=?", new String[] { name });
    }
    public String  queryOne(String name) {
        Cursor c = db.rawQuery("select * from Lock where name= ?", new String[]{name});
        while (c.moveToNext()) {
            return c.getString(1);
        }
        c.close();
        return "";
    }

}
