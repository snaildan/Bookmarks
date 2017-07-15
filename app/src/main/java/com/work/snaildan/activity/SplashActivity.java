package com.work.snaildan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by snaildan on 2017/7/2.
 */

public class SplashActivity extends Activity {
    //延迟3秒
    private static final long SPLASH_DELAY_MILLIS = 500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // 使用Handler的postDelayed方法，3秒后执行跳转到HomeIndexActivity
        new Handler().postDelayed(new Runnable() {
            public void run() {
                HomeIndex();
            }
        }, SPLASH_DELAY_MILLIS);
    }

    private void HomeIndex() {
        Intent intent = new Intent(SplashActivity.this, HomeIndexActivity.class);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();
    }
}
