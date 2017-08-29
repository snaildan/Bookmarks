package com.work.snaildan.dbclass;

/**
 * Created by snaildan on 2017/8/29.
 */

public class TableBudget {
    public int _id;
    public float BudgetMoney;
    public float Level1;
    public float Level2;
    public float Level3;
    public int WarnFlag;
    public long BudgetDate;

    public TableBudget() {

    }

    public TableBudget(float BudgetMoney, float Level1, float Level2, float Level3, int WarnFlag, long BudgetDate) {
        this.BudgetMoney = BudgetMoney;
        this.Level1 = Level1;
        this.Level2 = Level2;
        this.Level3 = Level3;
        this.WarnFlag = WarnFlag;
        this.BudgetDate = BudgetDate;
    }

    public int get_id() {
        return _id;
    }

    public float getBudgetMoney() {
        return BudgetMoney;
    }

    public void setBudgetMoney(float budgetMoney) {
        BudgetMoney = budgetMoney;
    }

    public float getLevel1() {
        return Level1;
    }

    public void setLevel1(float level1) {
        Level1 = level1;
    }

    public float getLevel2() {
        return Level2;
    }

    public void setLevel2(float level2) {
        Level2 = level2;
    }

    public float getLevel3() {
        return Level3;
    }

    public void setLevel3(float level3) {
        Level3 = level3;
    }

    public int getWarnFlag() {
        return WarnFlag;
    }

    public void setWarnFlag(int warnFlag) {
        WarnFlag = warnFlag;
    }

    public long getBudgetDate() {
        return BudgetDate;
    }

    public void setBudgetDate(Long budgetDate) {
        BudgetDate = budgetDate;
    }
}
