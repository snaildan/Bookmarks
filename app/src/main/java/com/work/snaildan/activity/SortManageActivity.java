package com.work.snaildan.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
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
        final SortManageAdp sortManageAdp = new SortManageAdp(this);
        //返回按钮
        re_top_button = (ImageView) findViewById(R.id.re_top_button);
        re_top_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SortManageActivity.this, MoreActivity.class);
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
                showEditDialog(v,sortManageAdp);
            }
        });
        //获取列表
        ListView listView = (ListView) findViewById(R.id.list_sort_manage);
        final ArrayList<TableSort> tableSorts = dbManage.sqlQuery("table_sort");
        sortManageAdp.setList(tableSorts);
        listView.setAdapter(sortManageAdp);
        //长按事件
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id) {
                final int pos = position;
                //系统项目不可删除
                if (pos < 11) {
                    return false;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(SortManageActivity.this);
                builder.setTitle("删除该条目");
                builder.setMessage("确认要删除该条目吗?");
                builder.setPositiveButton("删除",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {
                            //删除数据
                            TableSort tb_del = (TableSort)sortManageAdp.getItem(pos);
                            String tb_del_id_ = Integer.toString(tb_del.get_id());
                            String[] tb_del_id = new String[]{tb_del_id_};
                            dbManage.delById("table_sort",tb_del_id);
                            //Log.i("tb_del------", "onItemLongClick:"+pos+"数据库id："+tb_del.get_id());
                            tableSorts.remove(pos);
                            sortManageAdp.notifyDataSetChanged();
                            Toast.makeText(SortManageActivity.this,"删除成功！",Toast.LENGTH_SHORT).show();
                        }
                    });
                builder.setNegativeButton("取消", null);
                builder.create().show();
                return false;
            }
        });
    }

    //自定义对话框初始化
    public void showEditDialog(View view,SortManageAdp sortManageAdp) {
        sortAddDialog = new SortAddDialog(this,R.style.loading_dialog);
        sortAddDialog.setMAdapter(sortManageAdp);
        sortAddDialog.show();
    }

}
