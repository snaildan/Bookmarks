package com.work.snaildan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.work.snaildan.db.DbManager;

public class HomeIndexActivity extends Activity {
    private DbManager dbManage;
    private ImageView today_add;
    private ImageView main_account;
    private ImageView main_report;
    private ImageView main_more;
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
        sqlQuery();
        //String[] aa = new String[]{"19"};
        //dbManage.delById("table_sort",aa);
    }
    private void sqlQuery(){
        dbManage.sqlQuery("table_sort");
    }
}