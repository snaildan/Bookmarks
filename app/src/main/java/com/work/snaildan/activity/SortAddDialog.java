package com.work.snaildan.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.work.snaildan.MyAdapter.SortManageAdp;
import com.work.snaildan.MyAdapter.SpinnerAdapter;
import com.work.snaildan.db.DbManager;
import com.work.snaildan.dbclass.TableSort;

public class SortAddDialog extends Dialog {
    final Activity context;
    private Button btn_save;
    private Button btn_cancel;
    public EditText add_sortName;
    public String sort_spinner_str;
    public String editTextName;
    private Spinner sort_spinner;
    private DbManager dbManage;
    private String s_sortCode;
    private String s_type_s;
    private SortManageAdp sortManageAdp;

    public void setMAdapter(SortManageAdp sortManageAdp){
        this.sortManageAdp = sortManageAdp;
    }

    //默认皮肤theme不设定即可自行设置
    public SortAddDialog(Activity context, int theme) {
        super(context, theme);
        this.context = context;
        dbManage = new DbManager(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定布局
        this.setContentView(R.layout.dialog_sort_add);
        Window dialogWindow = this.getWindow();
        WindowManager m = context.getWindowManager();
        // 获取屏幕宽、高用
        Display d = m.getDefaultDisplay();
        // 获取对话框当前的参数值
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        p.height = (int) (d.getHeight() * 0.35); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.7); // 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(p);

        // 根据id在布局中找到控件对象
        btn_save = (Button) findViewById(R.id.btn_save_pop);
        btn_cancel = (Button) findViewById(R.id.btn_cancel_pop);
        // 为确定按钮绑定点击事件监听器
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_sortName = (EditText) findViewById(R.id.add_sortName);
                String add_sortName_str = add_sortName.getText().toString();
                if(add_sortName_str.equals("")){
                    //Log.i("Debug_sortAdd-------","没有输入类别名称");
                    Toast.makeText(context,"请输入类别名称！",Toast.LENGTH_SHORT).show();
                }else{
                    //Log.i("Debug_sortAdd-------","输入数据为："+add_sortName_str);
                    //信息入库
                    int s_type_i = sort_spinner.getSelectedItemPosition();
                    if(s_type_i == 1){
                        s_sortCode = "100";
                        s_type_s = "1";
                    }else{
                        s_sortCode = "101";
                        s_type_s = "0";
                    }
//                    TableSort tableSort = new TableSort(s_sortCode,add_sortName_str,s_type_s,1,"R.drawable.icon_zdy");
//                    dbManage.add(tableSort);
                    TableSort tableSort1 = new TableSort(s_sortCode, "工资", "1", 0, "R.drawable.icon_gz");
                    dbManage.add(tableSort1);
                    TableSort tableSort2 = new TableSort(s_sortCode, "奖金", "1", 0, "R.drawable.icon_jj");
                    dbManage.add(tableSort2);
                    TableSort tableSort3 = new TableSort(s_sortCode, "股票", "1", 0, "R.drawable.icon_gp");
                    dbManage.add(tableSort3);
                    TableSort tableSort4 = new TableSort(s_sortCode, "礼物", "1", 0, "R.drawable.icon_sl");
                    dbManage.add(tableSort4);
                    TableSort tableSort5 = new TableSort(s_sortCode, "吃饭", "0", 0, "R.drawable.icon_cf");
                    dbManage.add(tableSort5);
                    TableSort tableSort6 = new TableSort(s_sortCode, "购物", "0", 0, "R.drawable.icon_gw");
                    dbManage.add(tableSort6);
                    TableSort tableSort7 = new TableSort(s_sortCode, "娱乐", "0", 0, "R.drawable.icon_yule");
                    dbManage.add(tableSort7);
                    TableSort tableSort8 = new TableSort(s_sortCode, "交通", "0", 0, "R.drawable.icon_jt");
                    dbManage.add(tableSort8);
                    TableSort tableSort9 = new TableSort(s_sortCode, "通信", "0", 0, "R.drawable.icon_tx");
                    dbManage.add(tableSort9);
                    TableSort tableSort19 = new TableSort(s_sortCode, "医疗", "0", 0, "R.drawable.icon_yl");
                    dbManage.add(tableSort19);
                    TableSort tableSort10 = new TableSort(s_sortCode, "捐款", "0", 0, "R.drawable.icon_jz");
                    dbManage.add(tableSort10);
                    dbManage.closeDB();
                    Toast.makeText(context,add_sortName_str+"类别保存成功！",Toast.LENGTH_SHORT).show();
                    //sortManageAdp传入并将新项目add
                    //sortManageAdp.addList(tableSort);
                    dismiss();
                }
            }
        });
        // 为取消按钮绑定点击事件监听器
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        this.setCancelable(true);

        //类型下拉框
        sort_spinner=(Spinner)findViewById(R.id.sort_spinner);
        initSortSpinner();

    }
    //类型下拉框数据初始化
    private void initSortSpinner() {
        int[] typePics = {R.drawable.icon_qtzx_qtzc,R.drawable.icon_qtsr};
        String[] types = {"支出","收入"};
        SpinnerAdapter adapter = new SpinnerAdapter(context,typePics,types);
        sort_spinner.setAdapter(adapter);
        sort_spinner.setSelection(0, true);
        sort_spinner.setOnItemSelectedListener(new SpinnerOnSelectedListener());
    }

    class SpinnerOnSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> adapterView, View view, int position,long id) {
            if(adapterView.getId() == R.id.sort_spinner){
                LinearLayout ll = (LinearLayout)view;
                TextView tv = (TextView)ll.findViewWithTag("tagSpinnerView");
                sort_spinner_str = (String)tv.getText();
                //Log.i("Spinner----","selected===========>"+ sort_spinner_str);
            }
        }
        public void onNothingSelected(AdapterView<?> arg0) {
            //Log.i("Spinner----","selected===========>Nothing");
        }
    }
}