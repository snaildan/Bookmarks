package com.work.snaildan.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by snaildan on 2017/7/2.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "bookmarks_db.db";
    private static final int DB_VERSION = 1;
    public  DbHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS table_account" + "(_id INTEGER PRIMARY KEY AUTOINCREMENT ,AccMoney MONEY,NoteDate INTEGER,SortCode VARCHAR(10),Remark TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS table_sort" + "(_id INTEGER PRIMARY KEY AUTOINCREMENT ,SortCode VARCHAR(10),SortName VARCHAR(10),Type VARCHAR(10),State INTEGER,Icon VARCHAR(50))");
        db.execSQL("CREATE TABLE IF NOT EXISTS table_budget" + "(_id INTEGER PRIMARY KEY AUTOINCREMENT ,BudgetDate INTEGER,BudgetMoney MONEY,WarnFlag INTEGER,Level1 DOUBLE,Level2 DOUBLE,Level3 DOUBLE)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("ALTER TABLE person ADD COLUMN info VARCHAR");
    }
}
