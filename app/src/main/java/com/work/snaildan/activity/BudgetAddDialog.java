package com.work.snaildan.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.work.snaildan.db.DbManager;
import com.work.snaildan.dbclass.TableBudget;
import com.work.snaildan.tools.Utools;

import java.util.ArrayList;
import java.util.List;

public class BudgetAddDialog extends Dialog {
    final Activity context;
    private DbManager dbManage;
    private Button btn_save;
    private Button btn_cancel;
    private TextView budget_text;
    private EditText budget_editText;
    private CheckBox budget_open5;
    private CheckBox budget_open9;
    private CheckBox budget_open100;
    private List<CheckBox> checkBoxList = new ArrayList<CheckBox>();

    public Utools utools;

    public float BudgetMoney;
    public float Level1;
    public float Level2;
    public float Level3;
    public int WarnFlag = 0;
    public int BudgetDate;

    //默认皮肤theme不设定即可自行设置
    public BudgetAddDialog(Activity context, int theme) {
        super(context, theme);
        this.context = context;
        dbManage = new DbManager(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_budget_add);

        budget_open5 = (CheckBox) findViewById(R.id.budget_open5);
        budget_open9 = (CheckBox) findViewById(R.id.budget_open9);
        budget_open100 = (CheckBox) findViewById(R.id.budget_open100);
        budget_editText = (EditText) findViewById(R.id.budget_editText);
        budget_text = (TextView) findViewById(R.id.budget_text);
        utools = new Utools();
        String dateStr = utools.getTimestamp("yyyy-MM");
        budget_text.setText(dateStr + "预算：");

        Window dialogWindow = this.getWindow();
        WindowManager m = context.getWindowManager();
        //获取屏幕宽、高用
        Display d = m.getDefaultDisplay();
        //获取对话框当前的参数值
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        p.height = (int) (d.getHeight() * 0.55); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.75); // 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(p);

        //保存、取消按钮声明
        btn_save = (Button) findViewById(R.id.btn_save_pop);
        btn_cancel = (Button) findViewById(R.id.btn_cancel_pop);
        //为取消按钮绑定点击事件监听器
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        this.setCancelable(true);
        // 为确定按钮绑定点击事件监听器
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editStr = budget_editText.getText().toString();
                if (editStr.equals("")) {
                    Toast.makeText(context, "请输入预算金额！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isNumber(editStr)) {
                    Toast.makeText(context, "请输入正确的金额！", Toast.LENGTH_SHORT).show();
                    return;
                }
                BudgetMoney = Integer.parseInt(editStr);
                if (budget_open5.isChecked() == true) {
                    Level1 = BudgetMoney / 2;
                    WarnFlag = 1;
                } else {
                    Level1 = 0f;
                }
                if (budget_open9.isChecked() == true) {
                    Level2 = BudgetMoney * 0.9f;
                    WarnFlag = 1;
                } else {
                    Level2 = 0f;
                }
                if (budget_open100.isChecked() == true) {
                    Level3 = BudgetMoney;
                    WarnFlag = 1;
                } else {
                    Level3 = 0f;
                }
                String dateStr = utools.getTimestamp("yyyy-MM-dd");
                long BudgetDateLong = utools.StringToU(dateStr, "yyyy-MM");
                Log.i("-----", "d----" + dateStr + "---" + BudgetDateLong);
                //如果已有就更新
                boolean inflag = dbManage.queryBudget(BudgetDateLong);
                if (!inflag) {
                    TableBudget tablebudget = new TableBudget(BudgetMoney, Level1, Level2, Level3, WarnFlag, BudgetDateLong);
                    dbManage.add(tablebudget);
                } else {
                    dbManage.updateBudget(BudgetMoney, Level1, Level2, Level3, WarnFlag, BudgetDateLong);
                }
                //dbManage.sqlQuery("table_budget");
                dbManage.closeDB();
            }
        });
    }

    /**
     * 判断字符串是否是数字
     */
    public static boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }

    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是浮点数
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains("."))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}