package com.work.snaildan.tools;

import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.R.attr.format;

/**
 * Created by snaildan on 2017/7/16.
 */

public class Utools {
    //2017-08-08字符串转时间戳
    public long StringToU(String stringTime,String timeFromat) {
        SimpleDateFormat format = new SimpleDateFormat(timeFromat);
        Date date = null;
        try {
            date = format.parse(stringTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.i("Utools----", "StringToU----字符串："+stringTime +" 转换后: "+ date.getTime());
        return date.getTime();
    }
    //通过图片名称flow_icon_revenue，获取图片的资源id
    public int getResource(Context ctx,String imageName) {
        imageName = imageName.substring(11,imageName.length());
        int resId = ctx.getResources().getIdentifier(imageName, "drawable", ctx.getPackageName());
        return resId;
    }
    //获取当前时间：2017-07 or 07 or 2017-07-08
    //FormatStr = "yyyy年-MM月-dd日" or "yyyy-MM" ......
    public String getTimestamp(String FormatStr) {
        SimpleDateFormat getMonthFormat = new SimpleDateFormat(FormatStr);
        String date = getMonthFormat.format(new java.util.Date());
        return date;
    }
    //获取当前月第一天。用于数据库查询日期区间
    public String getMonthFirstDay(){
        SimpleDateFormat formatStr = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);
        String first = formatStr.format(c.getTime());
        //Log.i("Utools----", "getMonthDay----当前月第一天："+first);
        return first;
    }
    //获取当前月最后一天。用于数据库查询日期区间
    public String getMonthLastDay(){
        SimpleDateFormat formatStr = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = formatStr.format(ca.getTime());
        //Log.i("Utools----", "getMonthDay----当前月最后一天："+last);
        return last;
    }
    //时间戳转日期字符串
    public static String stampToDate(long dTime){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(dTime);
        res = simpleDateFormat.format(date);
        return res;
    }
}
