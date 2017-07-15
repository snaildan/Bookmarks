package com.work.snaildan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.work.snaildan.MyAdapter.AccountAdp;
import com.work.snaildan.MyAdapter.SortManageAdp;
import com.work.snaildan.db.DbManager;
import com.work.snaildan.dbclass.TableAccount;
import com.work.snaildan.dbclass.TableSort;

import java.util.ArrayList;

public class AccountActivity extends Activity {
    private ImageView re_top_button;
    private TextView top_title;
    private ImageView re_top_done_i;
    private TextView re_top_done_t;

    private DbManager dbManage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        dbManage = new DbManager(this);
        final AccountAdp accountAdp = new AccountAdp(this);
        //返回按钮
        re_top_button = (ImageView)findViewById(R.id.re_top_button);
        re_top_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this,HomeIndexActivity.class);
                startActivity(intent);
            }
        });
        //设置文字显示
        top_title = (TextView)findViewById(R.id.top_title);
        top_title.setText("流水");
        //显示隐藏的保存按钮
        re_top_done_i = (ImageView) findViewById(R.id.re_top_done_i);
        re_top_done_t = (TextView) findViewById(R.id.re_top_done_t);
        re_top_done_i.setVisibility(View.VISIBLE);
        re_top_done_t.setVisibility(View.VISIBLE);
        //设置文字显示
        re_top_done_t = (TextView) findViewById(R.id.re_top_done_t);
        re_top_done_t.setText("记账");
        re_top_done_i.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });
        //获取列表
        ListView listView = (ListView) findViewById(R.id.list_account);
        final ArrayList<TableAccount> tableAccount = dbManage.sqlQuery("table_account");
        accountAdp.setList(tableAccount);
        listView.setAdapter(accountAdp);

    }
}
