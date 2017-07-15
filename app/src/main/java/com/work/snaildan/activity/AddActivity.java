package com.work.snaildan.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
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

import com.work.snaildan.MyAdapter.SpinnerAdapter;
import com.work.snaildan.db.DbManager;
import com.work.snaildan.dbclass.TableSort;

import java.util.ArrayList;
import java.util.Calendar;

public class AddActivity extends Activity {
    private ImageView re_top_button;
    private TextView top_title;
    private ImageView re_top_done_i;
    private TextView re_top_done_t;
    public EditText add_mark;
    public EditText add_account;
    private Spinner spinner_type;
    private Spinner spinner_sort;
    private Button add_PickDate;
    public String spinner_type_str;
    public String spinner_sort_str;
    public Button add_save;
    private int mYear;
    private int mMonth;
    private int mDay;
    static final int DATE_DIALOG_ID = 0;
    public String mMonthChar;
    public String mDayChar;
    private DbManager dbManage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
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
        initSortSpinner();
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
        add_save = (Button) findViewById(R.id.add_save);
        //保存按钮
        add_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String add_mark_str = add_mark.getText().toString();
                String add_account_str = add_account.getText().toString();
                String add_PickDate_str = add_PickDate.getText().toString();
                //入库

                Intent intent = new Intent(AddActivity.this,HomeIndexActivity.class);
                startActivity(intent);
            }
        });
    }
    //类型下拉框数据初始化
    private void initTypeSpinner() {
        int[] typePics = {R.drawable.icon_qtzx_qtzc,R.drawable.icon_qtsr};
        String[] types = {"支出","收入"};
        SpinnerAdapter adapter = new SpinnerAdapter(this,typePics,types);
        spinner_type.setAdapter(adapter);
        spinner_type.setSelection(0, true);
        spinner_type.setOnItemSelectedListener(new SpinnerOnSelectedListener());
    }
    //类别下拉框数据初始化
    private void initSortSpinner() {
        dbManage = new DbManager(this);
        ArrayList<TableSort> tableSorts = dbManage.sqlQuery("table_sort");
        int[] sorts_pic = new int[tableSorts.size()];
        String[] sorts_name = new String[tableSorts.size()];
        for(int i = 0;i < tableSorts.size(); i ++){
            sorts_name[i] = tableSorts.get(i).getSortName();
            sorts_pic[i] = getResource(tableSorts.get(i).getIcon());
            Log.i("------","sorts_pic："+tableSorts.get(i).getIcon()+" sorts_picid："+getResource(tableSorts.get(i).getIcon()));
        }
        SpinnerAdapter adapter = new SpinnerAdapter(this,sorts_pic,sorts_name);
        spinner_sort.setAdapter(adapter);
        spinner_sort.setSelection(0, true);
        spinner_sort.setOnItemSelectedListener(new SpinnerOnSelectedListener());
    }
    class SpinnerOnSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> adapterView, View view, int position,long id) {
            if(adapterView.getId() == R.id.add_spinner_type){
                LinearLayout ll = (LinearLayout)view;
                TextView tv = (TextView)ll.findViewWithTag("tagSpinnerView");
                spinner_type_str = (String)tv.getText();
                Log.i("spinner_type----","selected===========>"+ spinner_type_str);
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
    //获取图片名称获取图片的资源id的方法
    public int getResource(String imageName) {
        Context ctx = this;
        imageName = imageName.substring(11,imageName.length());
        int resId = ctx.getResources().getIdentifier(imageName, "drawable", ctx.getPackageName());
        return resId;
    }
}
