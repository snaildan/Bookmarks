package com.work.snaildan.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.work.snaildan.dbclass.TableAccount;
import com.work.snaildan.dbclass.TableSort;
import com.work.snaildan.tools.Utools;

import java.util.ArrayList;
import java.util.Date;


public class DbManager{
    private DbHelper dbHelper;
    private SQLiteDatabase db;
    private String table_account = "table_account";
    private String table_sort = "table_sort";
    private String table_budget = "table_budget";
    public Utools utools;

    public DbManager(Context context){
        utools = new Utools();
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
                cv.put("Type",((TableAccount) t).getType());
                db.insert(this.table_account,null,cv);
                Log.i("db-add----","SortCode="+((TableAccount) t).getSortCode()+" Type="+((TableAccount) t).getType()+" AccMoney="+((TableAccount) t).getAccMoney()+" Remark="+((TableAccount) t).getRemark()+" NoteDate="+((TableAccount) t).getNoteDate());
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
            Cursor c =db.rawQuery("select * from table_account where _id >= ? order by NoteDate desc,_id desc",new String[]{"0"});
            while (c.moveToNext()){
                TableAccount tableAccount = new TableAccount();
                tableAccount._id  = c.getInt(c.getColumnIndex("_id"));
                tableAccount.AccMoney = c.getFloat(c.getColumnIndex("AccMoney"));
                tableAccount.SortCode = c.getString(c.getColumnIndex("SortCode"));
                tableAccount.Remark = c.getString(c.getColumnIndex("Remark"));
                tableAccount.Type = c.getString(c.getColumnIndex("Type"));
                tableAccount.NoteDate = c.getLong(c.getColumnIndex("NoteDate"));
                Log.i("db-query----","_id="+tableAccount._id+" SortCode="+tableAccount.SortCode+" Type="+tableAccount.Type+" AccMoney="+tableAccount.AccMoney+" NoteDate="+tableAccount.NoteDate+" Remark="+tableAccount.Remark);
                tableAccounts.add(tableAccount);
            }
            c.close();
            return tableAccounts;
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
    //按类型取出类别表中数据
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
    //本月收入、支出总额
    public String monthTotal(String Type){
        String total = null;
        String monthFirstDay = utools.getMonthFirstDay();
        String monthLastDay = utools.getMonthLastDay();
        long firstLong = utools.StringToU(monthFirstDay,"yyyy-MM-dd");
        long lastLong = utools.StringToU(monthLastDay,"yyyy-MM-dd");
        Log.i("DB-monthTotal----","当前月第一天的时间戳："+firstLong);
        Log.i("DB-monthTotal----","当前月最后一天的时间戳："+lastLong);
        //转字符串
        String firstString = firstLong + "";
        String lastString = lastLong + "";
        Cursor c = db.rawQuery("select sum(AccMoney) as totalin from table_account where Type = ? and NoteDate > ? and NoteDate < ?", new String[]{Type,firstString,lastString});
        while (c.moveToNext()) {
            total = c.getString(c.getColumnIndex("totalin"));
        }
        Log.i("table_account----","每月支出、收入total："+total);
        c.close();
        if(total == null){
            total = "0.0";
        }
        return total;
    }
    //本日收入、支出总额
    public String todayTotal(String Type){
        String total = null;
        String dateStr = utools.getTimestamp("yyyy-MM-dd");
        long todaylong = utools.StringToU(dateStr,"yyyy-MM-dd");
        String today = todaylong + "";
        Cursor c = db.rawQuery("select sum(AccMoney) as totalin from table_account where Type = ? and NoteDate = ?", new String[]{Type,today});
        while (c.moveToNext()) {
            total = c.getString(c.getColumnIndex("totalin"));
        }
        Log.i("table_account----","每日支出、收入total："+total);
        if(total == null){
            total = "0.0";
        }
        return total;
    }
    //按日期、统计查询收入、支出
    public ArrayList typeTotal(String Type,String startD,String endD){
        long startDlong = utools.StringToU(startD,"yyyy-MM-dd");
        String startDs = startDlong + "";
        long endDlong = utools.StringToU(endD,"yyyy-MM-dd");
        String endDs = endDlong + "";
        Cursor c;
        if(Type.equals("2")){
            c = db.rawQuery("select SortCode,sum(AccMoney) as totalin from table_account where NoteDate >= ? and NoteDate <= ? group by SortCode", new String[]{startDs,endDs});
        }else{
            c = db.rawQuery("select SortCode,sum(AccMoney) as totalin from table_account where Type = ? and NoteDate >= ? and NoteDate <= ? group by SortCode", new String[]{Type,startDs,endDs});
        }
        ArrayList<TableAccount> tableAccounts = new ArrayList<>();
        while (c.moveToNext()) {
            TableAccount tableAccount = new TableAccount();
            tableAccount.AccMoney = c.getFloat(c.getColumnIndex("totalin"));
            tableAccount.SortCode = c.getString(c.getColumnIndex("SortCode"));

            Log.i("table_account----","total："+tableAccount.AccMoney);
            Log.i("table_account----","类型："+tableAccount.SortCode);
            tableAccounts.add(tableAccount);
        }
        c.close();
        return tableAccounts;
    }
    //按日期、统计查询收入、支出总额
    public Float reportTotal(String Type,String startD,String endD){
        Float total = null;
        long startDLong = utools.StringToU(startD,"yyyy-MM-dd");
        long endDLong = utools.StringToU(endD,"yyyy-MM-dd");
        String startDLongS = startDLong + "";
        String endDLongS = endDLong + "";
        Cursor c;
        if(Type.equals("2")) {
            c = db.rawQuery("select sum(AccMoney) as totalin from table_account where NoteDate >= ? and NoteDate <= ?", new String[]{startDLongS, endDLongS});
        }else{
            c = db.rawQuery("select sum(AccMoney) as totalin from table_account where Type = ? and NoteDate >= ? and NoteDate <= ?", new String[]{Type, startDLongS, endDLongS});
        }
        while (c.moveToNext()) {
            total = c.getFloat(c.getColumnIndex("totalin"));
        }
        if(total == null){
            total = 0.0f;
        }
        return total;
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
