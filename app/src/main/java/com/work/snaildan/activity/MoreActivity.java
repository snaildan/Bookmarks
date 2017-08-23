package com.work.snaildan.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.work.snaildan.db.DatabaseBak;

import java.io.File;

public class MoreActivity extends Activity implements View.OnClickListener{
    private ImageView more_sortmanager;
    private ImageView more_backup;
    private ImageView more_recovery;
    private ImageView more_about;
    private TextView top_title;
    private ImageView re_top_button;
    private ProgressDialog moreDialog;
    public int pCcount = 0;
    final int FINISH = 2;
    Handler mHandler;
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
                File DirName = getFilesDir();
                SQLiteDatabase mDb = openOrCreateDatabase("bookmarks_db.db", Context.MODE_PRIVATE, null);
                DatabaseBak dbBak = new DatabaseBak(mDb,DirName.getAbsolutePath(),"database.xml");
                dbBak.exportData();

                mHandler  =new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        if(msg.what == FINISH){
                            Toast.makeText(MoreActivity.this,"备份成功",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void dispatchMessage(Message msg) {
                        super.dispatchMessage(msg);
                    }
                };

                // 创建ProgressDialog对象
                moreDialog = new ProgressDialog(MoreActivity.this);
                // 设置进度条风格，风格为圆形，旋转的
                moreDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                // 设置ProgressDialog 标题
                moreDialog.setTitle("提示");
                // 设置ProgressDialog提示信息
                moreDialog.setMessage("数据库保存进度");
                // 设置ProgressDialog标题图标
                moreDialog.setIcon(R.drawable.budget_warning_icon);
                // 设置ProgressDialog 的进度条是否不明确 false 就是不设置为不明确
                moreDialog.setIndeterminate(false);
                // 设置ProgressDialog 进度条进度
                moreDialog.setProgress(100);
                // 让ProgressDialog显示
                moreDialog.show();
                //进度条进度递增
                pCcount = 0;
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            while (pCcount <= 100) {
                                //由线程来控制进度
                                pCcount += 7;
                                moreDialog.setProgress(pCcount);
                                Thread.sleep(100);
                            }
                            Message msg = mHandler.obtainMessage();
                            msg.what = FINISH;
                            mHandler.sendMessage(msg);
                            moreDialog.cancel();
                        } catch (Exception e) {
                            moreDialog.cancel();
                        }
                    }
                }.start();
                break;
            case R.id.more_recovery:
                Toast.makeText(MoreActivity.this,"还原成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.more_about:
                Intent intent = new Intent(MoreActivity.this,AboutActivity.class);
                startActivity(intent);
                break;
        }
        //内部存储默认路径
        //File fileDefault = getFilesDir();
        //Toast.makeText(MoreActivity.this,fileDefault.getAbsolutePath(),Toast.LENGTH_SHORT).show();
        //缓存目录
        //File fileCache = getCacheDir();
        //Toast.makeText(MoreActivity.this,fileCache.getAbsolutePath(),Toast.LENGTH_SHORT).show();
        //自定义文件夹路径
        //File fileMy = getDir("demo",MODE_PRIVATE);
        //Toast.makeText(MoreActivity.this,fileMy.getAbsolutePath(),Toast.LENGTH_SHORT).show();
        /*
        * 在指定目录建立文件
        * File DirName = getFilesDir();
        * File fileName = new File(DirName,"database.txt");
        *
        * */
        //数据库路径
        //this.getDatabasePath("bookmarks_db").getAbsolutePath();
    }


    class mBDialogListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // 点击“确定”按钮取消对话框
            dialog.cancel();
        }
    }
}
