package com.work.snaildan.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.work.snaildan.MyAdapter.SpinnerAdapter;
import com.work.snaildan.db.DbManager;
import com.work.snaildan.dbclass.TableAccount;
import com.work.snaildan.dbclass.TableSort;

import java.util.ArrayList;
import java.util.Calendar;

import com.work.snaildan.tools.Utools;

public class AddActivity extends Activity implements View.OnClickListener{
    private ImageView re_top_button;
    private TextView top_title;
    private ImageView re_top_done_i;
    private TextView re_top_done_t;
    public EditText add_mark;
    public int account;
    public EditText add_account;
    private Spinner spinner_type;
    private Spinner spinner_sort;
    private Button add_PickDate;
    public String spinner_type_str;
    public String spinner_sort_str;
    public Button add_continue;
    public Button add_save;
    private int mYear;
    private int mMonth;
    private int mDay;
    static final int DATE_DIALOG_ID = 0;
    public String mMonthChar;
    public String mDayChar;
    private DbManager dbManage;
    public Utools utools;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        utools = new Utools();
        //返回按钮
        re_top_button = (ImageView)findViewById(R.id.re_top_button);
        re_top_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this,HomeIndexActivity.class);
                startActivity(intent);
            }
        });
        //设置文字显示
        top_title = (TextView)findViewById(R.id.top_title);
        top_title.setText("记账");
        //显示隐藏的保存按钮
        re_top_done_i = (ImageView)findViewById(R.id.re_top_done_i);
        re_top_done_t = (TextView)findViewById(R.id.re_top_done_t);
        re_top_done_i.setVisibility(View.VISIBLE);
        re_top_done_t.setVisibility(View.VISIBLE);
        //记账金额
        add_account = (EditText)findViewById(R.id.add_account);
        //类型下拉框
        spinner_type = (Spinner)findViewById(R.id.add_spinner_type);
        spinner_sort = (Spinner)findViewById(R.id.add_spinner_sort);
        initTypeSpinner();
        initSortSpinner("0");
        //日期控件
        add_PickDate = (Button)findViewById(R.id.add_PickDate);
        add_PickDate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                showDialog(DATE_DIALOG_ID);
            }
        });
        final Calendar c = Calendar.getInstance();
        mYear=c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH);
        mDay=c.get(Calendar.DAY_OF_MONTH);
        updateDisplay();
        add_mark = (EditText) findViewById(R.id.add_mark);
        add_continue = (Button) findViewById(R.id.add_continue);
        add_save = (Button) findViewById(R.id.add_save);
        //保存、继续记账按钮
        re_top_done_i.setOnClickListener(this);
        re_top_done_i.setTag(1);
        add_save.setOnClickListener(this);
        add_save.setTag(2);
        add_continue.setOnClickListener(this);
        add_continue.setTag(3);
    }
    //类型下拉框数据初始化
    private void initTypeSpinner() {
        int[] typePics = {R.drawable.icon_xz,R.drawable.icon_qtzx_qtzc,R.drawable.icon_qtsr};
        String[] types = {"请选择","支出","收入"};
        SpinnerAdapter adapter = new SpinnerAdapter(this,typePics,types);
        spinner_type.setAdapter(adapter);
        spinner_type.setSelection(0, true);
        spinner_type.setOnItemSelectedListener(new SpinnerOnSelectedListener());
    }
    //类别下拉框数据初始化
    private void initSortSpinner(String type) {
        dbManage = new DbManager(this);
        ArrayList<TableSort> tableSorts = dbManage.QuerySortByType(type);
        int[] sorts_pic = new int[tableSorts.size()+1];
        String[] sorts_name = new String[tableSorts.size()+1];
        sorts_name[0] = "请选择";
        sorts_pic[0] = R.drawable.icon_xz;
        for(int i = 1;i < tableSorts.size()+1; i++){
            sorts_name[i] = tableSorts.get(i-1).getSortName();
            sorts_pic[i] = utools.getResource(this,tableSorts.get(i-1).getIcon());
            Log.i("------","sorts_pic："+tableSorts.get(i-1).getIcon()+" sorts_picid："+sorts_pic[i-1]);
        }
        SpinnerAdapter adapter = new SpinnerAdapter(this,sorts_pic,sorts_name);
        spinner_sort.setAdapter(adapter);
        spinner_sort.setSelection(0, true);
        spinner_sort.setOnItemSelectedListener(new SpinnerOnSelectedListener());
    }
    @Override
    public void onClick(View v) {
        int tag = (Integer) v.getTag();
        if(tag == 1 || tag == 2 || tag == 3){
            String add_mark_str = add_mark.getText().toString();
            String add_account_str = add_account.getText().toString();
            String add_PickDate_str = add_PickDate.getText().toString();
            if(add_account_str.equals("")){
                Toast.makeText(AddActivity.this,"请输入金额！",Toast.LENGTH_SHORT).show();
                return;
            }else{
                account = Integer.parseInt(add_account_str);
            }
            String spinner_type_str1 = "1";
            if(spinner_type_str == null || spinner_type_str.equals("请选择")){
                Toast.makeText(AddActivity.this,"请选择类型！",Toast.LENGTH_SHORT).show();
                return;
            }else if(spinner_type_str.equals("支出")){
                spinner_type_str1 = "0";
            }
            if(spinner_sort_str == null || spinner_sort_str.equals("请选择")){
                Toast.makeText(AddActivity.this,"请选择类别！",Toast.LENGTH_SHORT).show();
                return;
            }
            long dateTimeRe =  utools.StringToU(add_PickDate_str,"yyyy-MM-dd");
            //入库
            TableAccount tableAccount = new TableAccount(account,spinner_sort_str,add_mark_str,dateTimeRe,spinner_type_str1);
            dbManage.add(tableAccount);
            dbManage.closeDB();
            if(tag == 3){
                //刷新自己清空数据
                onCreate(null);
            }else {
                Intent intent = new Intent(AddActivity.this, HomeIndexActivity.class);
                startActivity(intent);
            }
        }
    }
    class SpinnerOnSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> adapterView, View view, int position,long id) {
            if(adapterView.getId() == R.id.add_spinner_type){
                LinearLayout ll = (LinearLayout)view;
                TextView tv = (TextView)ll.findViewWithTag("tagSpinnerView");
                spinner_type_str = (String)tv.getText();
                Log.i("spinner_type----","selected===========>"+ spinner_type_str);
                //按类型刷新类别表
                if(spinner_type_str.equals("支出")){
                    initSortSpinner("0");
                }else{
                    initSortSpinner("1");
                }

            }else if(adapterView.getId() == R.id.add_spinner_sort){
                LinearLayout ll = (LinearLayout)view;
                TextView tv = (TextView)ll.findViewWithTag("tagSpinnerView");
                spinner_sort_str = (String)tv.getText();
                Log.i("spinner_type----","selected===========>"+ spinner_sort_str);
            }
        }
        public void onNothingSelected(AdapterView<?> arg0) {
            Log.i("spinner_sort----","selected===========>Nothing");
        }
    }
    //日期控件
    private void updateDisplay(){
        if(mDay < 10){
            mDayChar = "0";
        }else{
            mDayChar = "";
        }
        if(mMonth < 9){
            mMonthChar = "0";
        }else{
            mMonthChar = "";
        }
        add_PickDate.setText(
            new StringBuilder()
                .append(mYear).append("-")
                .append(mMonthChar)
                .append(mMonth+1).append("-")
                .append(mDayChar)
                .append(mDay).append(" "));
    }
    //日期控件
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener(){
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDisplay();
        }
    };
    //日期控件
    protected Dialog onCreateDialog(int id){
        switch (id){
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,mDateSetListener, mYear, mMonth, mDay);
        }
        return null;
    }
}
