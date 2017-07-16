package com.work.snaildan.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.work.snaildan.db.DbManager;
import com.work.snaildan.tools.Utools;

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
    private TextView index_month;
    public Utools utools;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_index);
        dbManage = new DbManager(this);
        utools = new Utools();
        index_month = (TextView)findViewById(R.id.index_month);
        index_month.setText(utools.getTimestamp("M"));

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
        String total_in_str = "¥"+dbManage.monthTotal("1");
        String total_out_str = "¥"+dbManage.monthTotal("0");
        index_total_in.setText(total_in_str);
        index_total_out.setText(total_out_str);

        index_budget.setText("¥0.0");

        String today_in_str = "¥"+dbManage.todayTotal("1");
        String today_out_str = "¥"+dbManage.todayTotal("0");
        index_today_in.setText(today_in_str);
        index_today_out.setText(today_out_str);

        String dateStr = utools.getTimestamp("yyyy年MM月dd日");
        index_date.setText(dateStr);

        ImageView iv = (ImageView) findViewById(R.id.index_ld_b);

        //漏斗图片合成
        //遮罩
//        Bitmap mask = BitmapFactory.decodeResource(getResources(),R.drawable.half);
//        //设置背景画布
//        Bitmap background = BitmapFactory.decodeResource(getResources(),R.drawable.right_r);
//        Bitmap bitmap = Bitmap.createBitmap(background.getWidth(),background.getHeight(), background.getConfig());
//        //以此bitmap为基准，创建一个画布
//        Canvas canvas = new Canvas(bitmap);
//        Paint paint = new Paint();
//        paint.setAntiAlias(true);
//        canvas.drawBitmap(background, new Matrix(), paint);
//        //设置图片相交情况下的处理方式
//        //setXfermode：设置当绘制的图像出现相交情况时候的处理方式的,它包含的常用模式有：
//        //PorterDuff.Mode.SRC_IN 取两层图像交集部分,只显示上层图像
//        //PorterDuff.Mode.DST_IN 取两层图像交集部分,只显示下层图像
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawBitmap(mask, new Matrix(), paint);
//
//        iv.setImageBitmap(bitmap);


        Bitmap mask = BitmapFactory.decodeResource(getResources(),R.drawable.right_r);
        Bitmap background = BitmapFactory.decodeResource(getResources(),R.drawable.half);
        Bitmap background_bg = BitmapFactory.decodeResource(getResources(),R.drawable.loudou_bg);
        Bitmap bitmap = Bitmap.createBitmap(background_bg.getWidth(),background_bg.getHeight(), background_bg.getConfig());
        //以此bitmap为基准，创建一个画布
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(background, 70, 40, null);
        Paint paint = new Paint();
        //paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(mask, 35,40, paint);
        //paint.setXfermode(null);
        iv.setImageBitmap(bitmap);
    }
}