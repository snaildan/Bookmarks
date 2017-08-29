package com.work.snaildan.MyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.work.snaildan.activity.R;
import com.work.snaildan.dbclass.TableAccount;
import com.work.snaildan.tools.Utools;

import java.util.ArrayList;

/**
 * Created by snaildan on 2017/7/14.
 */

public class AccountAdp extends BaseAdapter {
    Context myContext;
    private ArrayList<TableAccount> list;

    public AccountAdp(Context context) {
        myContext = context;
    }

    public void setList(ArrayList<TableAccount> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }
    // 自定义控件集合
    public final class ListSortView {
        public ImageView acc_icon;
        public TextView acc_money;
        public TextView acc_code;
        public TextView acc_noteDate;
        public TextView acc_type;
    }

    @Override
    public int getCount() {
        return this.list.size();
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
        TableAccount bean = list.get(position);
        if (bean == null) {
            return new View(myContext);
        }
        // 自定义视图
        ListSortView listSortView = null;
        if (convertView == null) {
            listSortView = new ListSortView();
            // 获取listview_item布局文件的视图
            convertView = LayoutInflater.from(myContext).inflate(R.layout.list_account, null);
            //获取控件对象
            listSortView.acc_icon = (ImageView) convertView.findViewById(R.id.acc_icon);
            listSortView.acc_money = (TextView) convertView.findViewById(R.id.acc_money);
            listSortView.acc_noteDate = (TextView) convertView.findViewById(R.id.acc_noteDate);
            listSortView.acc_code = (TextView) convertView.findViewById(R.id.acc_code);
            listSortView.acc_type = (TextView) convertView.findViewById(R.id.acc_type);
            convertView.setTag(listSortView);
        } else {
            listSortView = (ListSortView) convertView.getTag();
        }
        String s_money = Float.toString(bean.getAccMoney());
        s_money = "金额：¥"+s_money;
        listSortView.acc_money.setText(s_money);
        String s_date = Long.toString(bean.getNoteDate());
        listSortView.acc_noteDate.setText(s_date);
        String acc_type = "类型："+bean.getSortCode();
        String acc_code = "类别：收入";
        listSortView.acc_icon.setBackgroundResource(R.drawable.flow_icon_revenue);
        if(bean.getType().equals("0")){
            acc_code = "类别：支出";
            listSortView.acc_icon.setBackgroundResource(R.drawable.flow_icon_outpay);
        }
        Utools utools = new Utools();
        String dTime = utools.stampToDate(bean.getNoteDate(), "yyyy-MM-dd");
        listSortView.acc_noteDate.setText(dTime);
        listSortView.acc_type.setText(acc_type);
        listSortView.acc_code.setText(acc_code);
        return convertView;
    }
}
