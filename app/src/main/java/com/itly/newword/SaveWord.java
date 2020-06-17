package com.itly.newword;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SaveWord extends SQLiteOpenHelper {

    public SaveWord(Context context) {
        super(context,"itly.db",null,1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE word(_id INTEGER PRIMARY KEY AUTOINCREMENT,word VARCHAR(20),meaning VARCHAR(20))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
