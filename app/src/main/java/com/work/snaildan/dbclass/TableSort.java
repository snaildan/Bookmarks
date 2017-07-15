package com.work.snaildan.dbclass;

/**
 * Created by snaildan on 2017/7/14.
 */

public class TableSort {
    public int _id;
    public String SortCode;
    public String SortName;
    public String Type;
    public int State;
    public String Icon;

    public TableSort() {

    }

    public TableSort(String sortCode, String sortName, String type, int state, String icon) {
        this.SortCode = sortCode;
        this.SortName = sortName;
        this.Type = type;
        this.State = state;
        this.Icon = icon;
    }

    public int get_id() {
        return _id;
    }

    public String getSortCode() {
        return SortCode;
    }

    public void setSortCode(String sortCode) {
        SortCode = sortCode;
    }

    public String getSortName() {
        return SortName;
    }

    public void setSortName(String sortName) {
        SortName = sortName;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }
}
