package com.work.snaildan.dbclass;

/**
 * Created by snaildan on 2017/7/15.
 */

public class TableAccount {
    public int _id;
    public float AccMoney;
    public String SortCode;//先对应table_sort：SortName
    public String Remark;
    public long NoteDate;
    public String Type;

    public TableAccount(){}

    public TableAccount(float accMoney, String sortCode, String remark, long noteDate,String type) {
        AccMoney = accMoney;
        SortCode = sortCode;
        Remark = remark;
        NoteDate = noteDate;
        Type = type;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int get_id() {
        return _id;
    }

    public float getAccMoney() {
        return AccMoney;
    }

    public void setAccMoney(float accMoney) {
        AccMoney = accMoney;
    }

    public String getSortCode() {
        return SortCode;
    }

    public void setSortCode(String sortCode) {
        SortCode = sortCode;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public long getNoteDate() {
        return NoteDate;
    }

    public void setNoteDate(int noteDate) {
        NoteDate = noteDate;
    }
}
