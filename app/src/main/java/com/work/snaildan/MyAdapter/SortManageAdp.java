package com.work.snaildan.MyAdapter;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.work.snaildan.activity.R;
import com.work.snaildan.dbclass.TableSort;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by snaildan on 2017/7/14.
 */

public class SortManageAdp extends BaseAdapter {
    Context myContext;
    private ArrayList<TableSort> list;

    public SortManageAdp(Context context) {
        myContext = context;
    }

    public void setList(ArrayList<TableSort> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public void addList(TableSort tableSort){
        if (this.list==null)
            return;
        if(!this.list.contains(tableSort)){
            this.list.add(tableSort);
        }
        this.notifyDataSetChanged();
    }

    // 自定义控件集合
    public final class ListSortView {
        public ImageView sort_icon;
        public TextView sort_name;
        public TextView sort_type;
        public TextView sort_state;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TableSort bean = list.get(position);
        if (bean == null) {
            return new View(myContext);
        }
        // 自定义视图
        ListSortView listSortView = null;
        if (convertView == null) {
            listSortView = new ListSortView();
            // 获取listview_item布局文件的视图
            convertView = LayoutInflater.from(myContext).inflate(R.layout.list_sort_manage, null);
            //获取控件对象
            listSortView.sort_icon = (ImageView) convertView.findViewById(R.id.sort_icon);
            listSortView.sort_name = (TextView) convertView.findViewById(R.id.sort_name);
            listSortView.sort_type = (TextView) convertView.findViewById(R.id.sort_type);
            listSortView.sort_state = (TextView) convertView.findViewById(R.id.sort_state);
            convertView.setTag(listSortView);
        } else {
            listSortView = (ListSortView) convertView.getTag();
        }
        //截取后的icon展示
        int res_id = getResource(bean.getIcon());
        Log.i("snail----",res_id+"====="+res_id);
        listSortView.sort_icon.setBackgroundResource(res_id);
        String s_name = "名称："+bean.getSortName();
        String s_type = bean.getType();
        int s_state = bean.getState();
        String s_state_str;
        if(s_type.equals("1")){
            s_type = "类型：收入";
        }else{
            s_type = "类型：支出";
        }
        if(s_state == 1){
            s_state_str = "状态：开启";
        }else{
            s_state_str = "状态：系统";
        }
        listSortView.sort_name.setText(s_name);
        listSortView.sort_type.setText(s_type);
        listSortView.sort_state.setText(s_state_str);
        return convertView;
    }


    /**
     * 获取图片名称获取图片的资源id的方法
     *
     * @param imageName
     * @return
     */
    public int getResource(String imageName) {
        Context ctx = myContext;
        imageName = imageName.substring(11,imageName.length());
        int resId = myContext.getResources().getIdentifier(imageName, "drawable", ctx.getPackageName());
        return resId;
    }
}
