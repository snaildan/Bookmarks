package com.work.snaildan.MyAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by snaildan on 2017/7/13.
 */

public class SpinnerAdapter extends BaseAdapter {
    private Context ctx;
    private int drawableIDs[];
    private String stringIDs[];

    public SpinnerAdapter(Context ctx, int DrawableIDs[], String StringIDs[]){
        this.ctx = ctx;
        this.drawableIDs = DrawableIDs;
        this.stringIDs = StringIDs;
    }

    public int getCount() {
        return drawableIDs.length ;
    }

    public Object getItem(int position) {
        return drawableIDs [position];
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout ll = new LinearLayout(ctx);
        //ll.setLayoutParams(new LinearLayout.LayoutParams(100,120));
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setGravity(Gravity.CENTER_VERTICAL);
        ImageView iv = new ImageView(ctx);
        iv.setImageResource(drawableIDs[position]);
        iv.setLayoutParams(new ViewGroup.LayoutParams(100,100));
        ll.addView(iv);
        TextView tv = new TextView(ctx);
        tv.setText(stringIDs[position]);
        tv.setTextSize(16);
        tv.setTextColor(Color.BLACK);
        tv.setTag("tagSpinnerView");
        ll.addView(tv);
        return ll;
    }
}
