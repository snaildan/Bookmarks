package com.work.snaildan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BudgetActivity extends Activity {
    private TextView top_title;
    private ImageView re_top_button;

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
    }
}
