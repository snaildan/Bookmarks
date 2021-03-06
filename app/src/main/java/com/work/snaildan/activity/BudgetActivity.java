package com.work.snaildan.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.work.snaildan.MyAdapter.BudgetAdp;
import com.work.snaildan.db.DbManager;
import com.work.snaildan.dbclass.TableBudget;

import java.util.ArrayList;

public class BudgetActivity extends Activity {
    private TextView top_title;
    private ImageView re_top_button;
    private ImageView budget_set;
    private CheckBox budget_open;
    private int budgetstat = 0;

    private SharedPreferences sp;

    private BudgetAddDialog AddDialog;
    private DbManager dbManage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        //设置文字显示
        top_title = (TextView) findViewById(R.id.top_title);
        top_title.setText("预算");
        //返回按钮
        re_top_button = (ImageView) findViewById(R.id.re_top_button);
        re_top_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BudgetActivity.this, HomeIndexActivity.class);
                startActivity(intent);
            }
        });

        //获取列表
        final BudgetAdp budgetAdp = new BudgetAdp(this);
        dbManage = new DbManager(this);
        ListView listView = (ListView) findViewById(R.id.list_budget);
        final ArrayList<TableBudget> tableBudget = dbManage.sqlQuery("table_budget");
        budgetAdp.setList(tableBudget);
        listView.setAdapter(budgetAdp);

        //预警状态
        budget_open = (CheckBox) findViewById(R.id.budget_open);
        sp = getSharedPreferences("sp_budget", Context.MODE_PRIVATE);
        budgetstat = sp.getInt("budgetStat", 0);
        //默认状态
        if (budgetstat == 1) {
            budget_open.setChecked(true);
        } else {
            budget_open.setChecked(false);
        }

        //设置预算弹出自定义对话框
        budget_set = (ImageView) findViewById(R.id.budget_set);
        budget_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog(v, budgetAdp);
            }
        });
    }

    @Override
    protected void onStop() {
        //预警状态保存
        super.onStop();
        if (budget_open.isChecked() == true) {
            budgetstat = 1;
        } else {
            budgetstat = 0;
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("budgetStat", budgetstat);
        editor.commit();
    }
    //自定义对话框初始化
    public void showEditDialog(View view, BudgetAdp budgetAdp) {
        AddDialog = new BudgetAddDialog(this, R.style.loading_dialog);
        AddDialog.setBAdapter(budgetAdp);
        AddDialog.show();
    }
}
