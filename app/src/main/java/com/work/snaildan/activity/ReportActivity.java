package com.work.snaildan.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import com.work.snaildan.db.DbManager;
import com.work.snaildan.dbclass.TableAccount;

public class ReportActivity extends Activity implements OnChartValueSelectedListener, View.OnClickListener {
    private ImageView re_top_button;
    private ImageView re_piechart;
    private ImageView income;
    private ImageView outpay;
    private Button report_enddate;
    private Button report_startdate;

    private int mType;
    public String Mstartdate;
    public String Menddate;

    private int mYear;
    private int mMonth;
    private int mDay;
    static final int DATE_DIALOG_ID_S = 0;
    static final int DATE_DIALOG_ID_E = 1;
    public String mMonthChar;
    public String mDayChar;
    private DbManager dbManage;
    public String sType = "2";
    public float rTotal;

    private PieChart mPieChart;
    //显示百分比
    private Button btn_show_percentage;
    //显示类型
    private Button btn_show_type;
    //x轴动画
    private Button btn_anim_x;
    //y轴动画
    private Button btn_anim_y;
    //xy轴动画
    private Button btn_anim_xy;
    //保存到sd卡
    private Button btn_save_pic;
    //显示中间文字
    private Button btn_show_center_text;
    //旋转动画
    private Button btn_anim_rotating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        re_top_button = (ImageView)findViewById(R.id.re_top_button);
        re_top_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportActivity.this,HomeIndexActivity.class);
                startActivity(intent);
            }
        });
        re_piechart = (ImageView)findViewById(R.id.re_piechart);
        re_piechart.setOnClickListener(this);
        outpay = (ImageView)findViewById(R.id.outpay);
        outpay.setOnClickListener(this);
        income = (ImageView)findViewById(R.id.income);
        income.setOnClickListener(this);

        //开始日期控件
        report_startdate = (Button)findViewById(R.id.report_startdate);
        report_startdate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                mType = 1;
                showDialog(DATE_DIALOG_ID_S);
            }
        });
        //结束日期控件
        report_enddate = (Button)findViewById(R.id.report_enddate);
        report_enddate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                mType = 2;
                showDialog(DATE_DIALOG_ID_E);
            }
        });
        final Calendar c = Calendar.getInstance();
        mYear=c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH);
        mDay=c.get(Calendar.DAY_OF_MONTH);

        Mstartdate = report_startdate.getText().toString();
        Menddate = report_enddate.getText().toString();
        initView(Mstartdate,Menddate);
    }
    //日期控件更新展示
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
        //两个日期控件的区分
        if(mType == 1) {
            report_startdate.setText(
                new StringBuilder()
                    .append(mYear).append("-")
                    .append(mMonthChar)
                    .append(mMonth + 1).append("-")
                    .append(mDayChar)
                    .append(mDay).append(" ")
            );
            Mstartdate = report_startdate.getText().toString();
        }else{
            report_enddate.setText(
                new StringBuilder()
                    .append(mYear).append("-")
                    .append(mMonthChar)
                    .append(mMonth + 1).append("-")
                    .append(mDayChar)
                    .append(mDay).append(" ")
            );
            Menddate = report_enddate.getText().toString();
        }
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
            case DATE_DIALOG_ID_S:
                mType = 1;
                return new DatePickerDialog(this,mDateSetListener, mYear, mMonth, mDay);
            case DATE_DIALOG_ID_E:
                mType = 2;
                return new DatePickerDialog(this,mDateSetListener, mYear, mMonth, mDay);
        }
        return null;
    }
    //统计图初始化View
    private void initView(String startD,String endD) {
        //数据库准备数据
        dbManage = new DbManager(this);
        //1收入，0支出
        ArrayList<TableAccount> tableAccount = dbManage.typeTotal(sType,startD,endD);
        //总数
        rTotal = dbManage.reportTotal(sType,startD,endD);

        //饼状图
        mPieChart = (PieChart) findViewById(R.id.mPieChart);
        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setExtraOffsets(5, 10, 5, 5);

        mPieChart.setDragDecelerationFrictionCoef(0.95f);
        //设置中间文件
        mPieChart.setCenterText(generateCenterSpannableText());

        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColor(Color.WHITE);

        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);

        mPieChart.setHoleRadius(58f);
        mPieChart.setTransparentCircleRadius(61f);

        mPieChart.setDrawCenterText(true);

        mPieChart.setRotationAngle(0);
        //触摸旋转
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(true);

        //变化监听
        mPieChart.setOnChartValueSelectedListener(this);
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        if(rTotal == 0.0) {
            //模拟数据
            entries.add(new PieEntry(100, "日期无数据"));
        }else{
            for(int i = 0;i < tableAccount.size();i++){
                TableAccount tableAccounts = (TableAccount) tableAccount.get(i);
                float ss = tableAccounts.getAccMoney()/rTotal*10;
                entries.add(new PieEntry(ss, tableAccounts.getSortCode()));
            }
        }
        //设置数据
        setData(entries);
        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        Legend l = mPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        // 输入标签样式
        mPieChart.setEntryLabelColor(Color.WHITE);
        mPieChart.setEntryLabelTextSize(12f);
    }
    //设置中间文字
    private SpannableString generateCenterSpannableText() {
        //原文：MPAndroidChart\ndeveloped by Philipp Jahoda
        SpannableString s = new SpannableString("理财大师\n选择日期进行查询");
        //s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        //s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        // s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        //s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        // s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        // s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }
    //设置数据
    private void setData(ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        //数据和颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mPieChart.setData(data);
        mPieChart.highlightValues(null);
        //刷新
        mPieChart.invalidate();
    }
    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }
    @Override
    public void onNothingSelected() {

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //统计按钮
            case R.id.re_piechart:
                sType = "2";
                initView(Mstartdate,Menddate);
                break;
            //支出按钮
            case R.id.outpay:
                sType = "0";
                initView(Mstartdate,Menddate);
                break;
            //收入按钮
            case R.id.income:
                sType = "1";
                initView(Mstartdate,Menddate);
                break;
            //显示百分比
            case R.id.btn_show_percentage:
                for (IDataSet<?> set : mPieChart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());
                mPieChart.invalidate();
                break;
            //显示类型
            case R.id.btn_show_type:
                if (mPieChart.isDrawHoleEnabled())
                    mPieChart.setDrawHoleEnabled(false);
                else
                    mPieChart.setDrawHoleEnabled(true);
                mPieChart.invalidate();
                break;
            //x轴动画
            case R.id.btn_anim_x:
                mPieChart.animateX(1400);
                break;
            //y轴动画
            case R.id.btn_anim_y:
                mPieChart.animateY(1400);
                break;
            //xy轴动画
            case R.id.btn_anim_xy:
                mPieChart.animateXY(1400, 1400);
                break;
            //保存到sd卡
            case R.id.btn_save_pic:
                mPieChart.saveToPath("title" + System.currentTimeMillis(), "");
                break;
            //显示中间文字
            case R.id.btn_show_center_text:
                if (mPieChart.isDrawCenterTextEnabled())
                    mPieChart.setDrawCenterText(false);
                else
                    mPieChart.setDrawCenterText(true);
                mPieChart.invalidate();
                break;
            //旋转动画
            case R.id.btn_anim_rotating:
                mPieChart.spin(1000, mPieChart.getRotationAngle(), mPieChart.getRotationAngle() + 360, Easing.EasingOption
                        .EaseInCubic);
                break;
        }
    }
}