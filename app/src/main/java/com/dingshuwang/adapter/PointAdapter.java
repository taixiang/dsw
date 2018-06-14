package com.dingshuwang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dingshuwang.R;
import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.model.PointItem;

import java.util.List;

/**
 * Created by tx on 2017/6/22.
 */

public class PointAdapter extends BaseAdapter {

    private List<PointItem.Point> mList;
    private BaseActivity activity;
    private LayoutInflater inflater;

    public PointAdapter(List<PointItem.Point> mList, BaseActivity activity) {
        this.mList = mList;
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(null == convertView){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_point,null);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_remark = (TextView) convertView.findViewById(R.id.tv_remark);
        holder.tv_point = (TextView) convertView.findViewById(R.id.tv_point);
        holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
        PointItem.Point item = mList.get(position);
        holder.tv_remark.setText(item.remark);
        holder.tv_point.setText(item.value+"");
        if(item.add_time.contains("T")){
            String[] times = item.add_time.split("T");
            holder.tv_time.setText(times[0]);

        }
        return convertView;
    }

    private class ViewHolder{
        private TextView tv_remark;
        private TextView tv_point;
        private TextView tv_time;
    }


}
