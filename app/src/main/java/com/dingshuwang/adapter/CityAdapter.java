package com.dingshuwang.adapter;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dingshuwang.base.BaseActivity;

import java.util.List;

/**
 * Created by Administrator on 2016/7/20.
 */
public class CityAdapter extends BaseAdapter {

    private List<String> list;
    private BaseActivity activity;

    public CityAdapter(List<String> list, BaseActivity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView textView = new TextView(activity);
        textView.setText(list.get(position));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = 5;
        params.bottomMargin = 5;
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
