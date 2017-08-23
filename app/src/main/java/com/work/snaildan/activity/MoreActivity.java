package com.work.snaildan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MoreActivity extends Activity implements View.OnClickListener{
    private ImageView more_sortmanager;
    private ImageView more_backup;
    private ImageView more_recovery;
    private ImageView more_about;
    private TextView top_title;
    private ImageView re_top_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        //返回按钮
        re_top_button = (ImageView)findViewById(R.id.re_top_button);
        re_top_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoreActivity.this,HomeIndexActivity.class);
                startActivity(intent);
            }
        });
        //设置文字显示
        top_title = (TextView)findViewById(R.id.top_title);
        top_title.setText("更多");
        //类别管理按钮
        more_sortmanager = (ImageView)findViewById(R.id.more_sortmanager);
        more_sortmanager.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoreActivity.this,SortManageActivity.class);
                startActivity(intent);
            }
        });
        //备份、还原、关于
        more_about = (ImageView)findViewById(R.id.more_about);
        more_backup = (ImageView)findViewById(R.id.more_backup);
        more_recovery = (ImageView)findViewById(R.id.more_recovery);
        more_backup.setOnClickListener(this);
        more_recovery.setOnClickListener(this);
        more_about.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_backup:
                Toast.makeText(MoreActivity.this,"备份成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.more_recovery:
                Toast.makeText(MoreActivity.this,"还原成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.more_about:
                Intent intent = new Intent(MoreActivity.this,AboutActivity.class);
                startActivity(intent);
                break;
        }
    }
}
