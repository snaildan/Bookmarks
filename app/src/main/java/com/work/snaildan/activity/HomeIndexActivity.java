package com.work.snaildan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.work.snaildan.db.DbManager;

public class HomeIndexActivity extends Activity {
    private DbManager dbManage;
    private ImageView today_add;
    private ImageView main_account;
    private ImageView main_report;
    private ImageView main_more;
    private TextView index_total_in;
    private TextView index_total_out;
    private TextView index_date;
    private TextView index_budget;
    private TextView index_today_in;
    private TextView index_today_out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_index);
        dbManage = new DbManager(this);
        today_add = (ImageView)findViewById(R.id.today_add);
        today_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeIndexActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });
        main_account = (ImageView)findViewById(R.id.main_account);
        main_account.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeIndexActivity.this,AccountActivity.class);
                //Intent intent = new Intent(Intent.ACTION_VIEW);
                //intent.setData(Uri.parse("https://www.baidu.com"));
                startActivity(intent);
            }
        });
        main_report = (ImageView)findViewById(R.id.main_report);
        main_report.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeIndexActivity.this,ReportActivity.class);
                startActivity(intent);
            }
        });
        main_more = (ImageView)findViewById(R.id.main_more);
        main_more.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeIndexActivity.this,MoreActivity.class);
                startActivity(intent);
            }
        });
        //deleteDatabase("bookmarks_db.db");
        //dbManage.QuerySortByType("1");
        //dbManage.sqlQuery("table_account");
        //String[] id = new String[]{"10"};
        //dbManage.delById("table_account",id);

        //首页信息填入
        index_total_in = (TextView) findViewById(R.id.index_total_in);
        index_total_out = (TextView) findViewById(R.id.index_total_out);
        index_date = (TextView) findViewById(R.id.index_date);
        index_budget = (TextView) findViewById(R.id.index_budget);
        index_today_in = (TextView) findViewById(R.id.index_today_in);
        index_today_out = (TextView) findViewById(R.id.index_today_out);

        //数据库取出首页数据
        String total_in_str = dbManage.monthTotal("1");
        String total_out_str = "ddddd";
        index_total_in.setText(total_in_str);
        index_total_out.setText(total_out_str);

        index_budget.setText("56789");

        index_today_in.setText("56789");
        index_today_out.setText("56789");

        index_date.setText("56789");
    }
}