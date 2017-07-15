package com.work.snaildan.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.work.snaildan.MyAdapter.SortManageAdp;
import com.work.snaildan.db.DbManager;
import com.work.snaildan.dbclass.TableSort;

import java.util.ArrayList;

public class SortManageActivity extends Activity {
    private TextView top_title;
    private ImageView re_top_button;
    private ImageView re_top_done_i;
    private TextView re_top_done_t;
    private SortAddDialog sortAddDialog;
    private DbManager dbManage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_manage);
        dbManage = new DbManager(this);
        SortManageAdp sortManageAdp = new SortManageAdp(this);
        //返回按钮
        re_top_button = (ImageView) findViewById(R.id.re_top_button);
        re_top_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SortManageActivity.this, HomeIndexActivity.class);
                startActivity(intent);
            }
        });
        //设置文字显示
        top_title = (TextView) findViewById(R.id.top_title);
        top_title.setText("类别管理");
        //显示隐藏的保存按钮
        re_top_done_i = (ImageView) findViewById(R.id.re_top_done_i);
        re_top_done_t = (TextView) findViewById(R.id.re_top_done_t);
        re_top_done_i.setVisibility(View.VISIBLE);
        re_top_done_t.setVisibility(View.VISIBLE);
        //设置文字显示
        re_top_done_t = (TextView) findViewById(R.id.re_top_done_t);
        re_top_done_t.setText("新增");
        //点击弹出自定义对话框
        re_top_done_i.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showEditDialog(v);
            }
        });
        //获取列表
        ArrayList<TableSort> tableSorts = dbManage.sqlQuery("table_sort");
        ListView listView = (ListView) findViewById(R.id.list_sort_manage);
        sortManageAdp.setList(tableSorts);
        listView.setAdapter(sortManageAdp);
    }
    //自定义对话框初始化
    public void showEditDialog(View view) {
        sortAddDialog = new SortAddDialog(this,R.style.loading_dialog);
        sortAddDialog.show();
    }

}
