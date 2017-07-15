package com.work.snaildan.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.work.snaildan.dbclass.TableAccount;
import com.work.snaildan.dbclass.TableSort;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class DbManager{
    private DbHelper dbHelper;
    private SQLiteDatabase db;
    private String table_account = "table_account";
    private String table_sort = "table_sort";
    private String table_budget = "table_budget";
    private TableSort tableSort;

    public DbManager(Context context){
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public <T> boolean add(T t){
        //事务开始
        db.beginTransaction();
        Date date = new Date();
        long datetime = date.getTime();
        String datetime_str = Long.toString(datetime);
        try{
            if(t instanceof TableSort){
                ContentValues cv =new ContentValues();
                cv.put("sortCode",((TableSort) t).getSortCode());
                cv.put("sortName",((TableSort) t).getSortName());
                cv.put("Type",((TableSort) t).getType());
                cv.put("State",((TableSort) t).getState());
                cv.put("Icon",((TableSort) t).getIcon());
                db.insert(this.table_sort,null,cv);
                Log.i("db-add----","SortCode= "+((TableSort) t).getSortCode()+"SortName= "+((TableSort) t).getSortName());
                db.setTransactionSuccessful();//设置事务
                return true;
            }else if(t instanceof TableAccount){
                ContentValues cv =new ContentValues();
                cv.put("AccMoney",((TableAccount) t).getAccMoney());
                cv.put("SortCode",((TableAccount) t).getSortCode());
                cv.put("Remark",((TableAccount) t).getRemark());
                cv.put("NoteDate",((TableAccount) t).getNoteDate());
                db.insert(this.table_account,null,cv);
                Log.i("db-add----","SortCode="+((TableAccount) t).getSortCode()+" AccMoney="+((TableAccount) t).getAccMoney()+" Remark="+((TableAccount) t).getRemark()+" NoteDate="+((TableAccount) t).getNoteDate());
                db.setTransactionSuccessful();
                return true;
           // }else if(t instanceof table_budget){

                //return true;
            }else {
                return false;
            }
        }finally {
            db.endTransaction();
        }
    }
    //遍历所有表中数据
    public ArrayList sqlQuery(String tableName){
        if(tableName.equals(table_account)) {
            ArrayList<TableAccount> tableAccounts = new ArrayList<>();
            Cursor c =db.rawQuery("select * from table_account where _id >= ?",new String[]{"0"});
            while (c.moveToNext()){
                TableAccount tableAccount = new TableAccount();
                tableAccount._id  = c.getInt(c.getColumnIndex("_id"));
                tableAccount.AccMoney = c.getFloat(c.getColumnIndex("AccMoney"));
                tableAccount.SortCode = c.getString(c.getColumnIndex("SortCode"));
                tableAccount.Remark = c.getString(c.getColumnIndex("Remark"));
                tableAccount.NoteDate = c.getLong(c.getColumnIndex("NoteDate"));
                Log.i("db-query----","_id="+tableAccount._id+" SortCode="+tableAccount.SortCode+" AccMoney="+tableAccount.AccMoney+" NoteDate="+tableAccount.NoteDate+" Remark="+tableAccount.Remark);
                tableAccounts.add(tableAccount);
            }
            return null;
        }else if(tableName.equals(table_sort)) {
            ArrayList<TableSort> tableSorts = new ArrayList<>();
            Cursor c_ = db.rawQuery("select * from table_sort where _id >= ?", new String[]{"0"});
            while (c_.moveToNext()) {
                TableSort tableSort = new TableSort();
                tableSort._id  = c_.getInt(c_.getColumnIndex("_id"));
                tableSort.SortCode = c_.getString(c_.getColumnIndex("SortCode"));
                tableSort.SortName = c_.getString(c_.getColumnIndex("SortName"));
                tableSort.Type = c_.getString(c_.getColumnIndex("Type"));
                tableSort.State = c_.getInt(c_.getColumnIndex("State"));
                tableSort.Icon = c_.getString(c_.getColumnIndex("Icon"));
                Log.i("db-query----", "_id=" + tableSort._id  + " SortName=" + tableSort.SortName + " Type=" + tableSort.Type + " SortCode=" + tableSort.SortCode+ " Icon=" + tableSort.Icon);
                tableSorts.add(tableSort);
            }
            c_.close();
            return tableSorts;
        }else if(tableName.equals(table_budget)){
            return null;
        }else{
            return null;
        }
    }
    //按类别取类别表中数据
    public ArrayList QuerySortByType(String Type) {
        ArrayList<TableSort> tableSorts = new ArrayList<>();
        Cursor c_ = db.rawQuery("select * from table_sort where Type = ?", new String[]{Type});
        while (c_.moveToNext()) {
            TableSort tableSort = new TableSort();
            tableSort._id  = c_.getInt(c_.getColumnIndex("_id"));
            tableSort.SortCode = c_.getString(c_.getColumnIndex("SortCode"));
            tableSort.SortName = c_.getString(c_.getColumnIndex("SortName"));
            tableSort.Type = c_.getString(c_.getColumnIndex("Type"));
            tableSort.State = c_.getInt(c_.getColumnIndex("State"));
            tableSort.Icon = c_.getString(c_.getColumnIndex("Icon"));
            Log.i("db-query----", "_id=" + tableSort._id  + " SortName=" + tableSort.SortName + " Type=" + tableSort.Type + " SortCode=" + tableSort.SortCode+ " Icon=" + tableSort.Icon);
            tableSorts.add(tableSort);
        }
        c_.close();
        return tableSorts;
    }

    //删除数据
    public void  delById(String tableName,String[] id){
        if(tableName.equals(table_account)) {
            db.delete(tableName,"_id=?",id);
            Log.i("del----","删除了id");
        }else if(tableName.equals(table_sort)) {
            db.delete(tableName,"_id=?",id);
            Log.i("del----","删除了id");
        }else if(tableName.equals(table_budget)){
            db.delete(tableName,"_id=?",id);
            Log.i("del----","删除了id");
        }
    }

    public void closeDB(){
        db.close();
    }

    //删除数据库
    public void deleteDatabase(String Name){
        deleteDatabase(Name);
    }
}
