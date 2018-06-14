package com.dingshuwang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dingshuwang.R;
import com.dingshuwang.base.BaseActivity;

/**
 * Created by Administrator on 2016/7/24.
 */
public class StatusAdapter extends BaseAdapter {

    private String[] strs;
    private BaseActivity activity;
    private LayoutInflater inflater;

    public StatusAdapter(String[] strs, BaseActivity activity) {
        this.strs = strs;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return strs.length;
    }

    @Override
    public Object getItem(int position) {
        return strs[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextItem textItem = null;
        if(convertView == null){
            textItem = new TextItem();
            convertView = inflater.inflate(R.layout.one_text_item,null);
            convertView.setTag(textItem);
        }else {
            textItem = (TextItem) convertView.getTag();
        }
        textItem.textView = (TextView) convertView.findViewById(R.id.text);

        textItem.textView.setText(strs[position]);
        return convertView;
    }

    private class TextItem{
        TextView textView;
    }

}
