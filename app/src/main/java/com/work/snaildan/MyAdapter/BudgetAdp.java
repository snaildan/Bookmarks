package com.work.snaildan.MyAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.work.snaildan.activity.R;
import com.work.snaildan.db.DbManager;
import com.work.snaildan.dbclass.TableBudget;
import com.work.snaildan.tools.Utools;

import java.util.ArrayList;

/**
 * Created by zhangdan on 2017/8/31.
 */
public class BudgetAdp extends BaseAdapter {
    Context myContext;
    private DbManager dbManage;
    private ArrayList<TableBudget> list;
    public Utools utools;

    public BudgetAdp(Context context) {
        myContext = context;
    }

    public void setList(ArrayList<TableBudget> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public void addList(TableBudget tableBudget) {
        if (this.list == null)
            return;
        if (!this.list.contains(tableBudget)) {
            //新增
            //this.list.add(tableBudget);
            //更新
            this.list.set(0, tableBudget);
        }
        this.notifyDataSetChanged();
    }

    // 自定义控件集合
    public final class ListSortView {
        public ImageView budget_icon;
        public TextView budget_date;
        public TextView budget_value;
        public TextView budget_pay;
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
        TableBudget bean = list.get(position);
        if (bean == null) {
            return new View(myContext);
        }
        // 自定义视图
        ListSortView listSortView = null;
        if (convertView == null) {
            listSortView = new ListSortView();
            // 获取listview_item布局文件的视图
            convertView = LayoutInflater.from(myContext).inflate(R.layout.list_budget, null);
            //获取控件对象
            listSortView.budget_icon = (ImageView) convertView.findViewById(R.id.budget_icon);
            listSortView.budget_date = (TextView) convertView.findViewById(R.id.budget_date);
            listSortView.budget_value = (TextView) convertView.findViewById(R.id.budget_value);
            listSortView.budget_pay = (TextView) convertView.findViewById(R.id.budget_pay);
            convertView.setTag(listSortView);
        } else {
            listSortView = (ListSortView) convertView.getTag();
        }
        //赋值
        listSortView.budget_icon.setBackgroundResource(R.drawable.budget_warning_icon);
        String budgetMoney = "预算：￥" + bean.getBudgetMoney() + "";
        Log.i("snail----", "budgetMoney===== " + budgetMoney);
        listSortView.budget_value.setText(budgetMoney);
        //时间
        utools = new Utools();
        String date_str = utools.stampToDate(bean.getBudgetDate(), "yyyy-MM");
        listSortView.budget_date.setText(date_str);
        //当月支出
        dbManage = new DbManager(this.myContext);
        String out_str = "支出：￥" + dbManage.monthDTotal(bean.getBudgetDate());
        listSortView.budget_pay.setText(out_str);
        return convertView;
    }
}
