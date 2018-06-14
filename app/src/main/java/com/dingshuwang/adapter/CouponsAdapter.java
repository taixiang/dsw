package com.dingshuwang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dingshuwang.R;
import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.model.CouponsItem;

import java.util.List;

/**
 * Created by tx on 2017/6/22.
 */

public class CouponsAdapter extends BaseAdapter {

    private List<CouponsItem.Coupon> mList;
    private BaseActivity activity;
    private LayoutInflater inflater;

    public CouponsAdapter(List<CouponsItem.Coupon> mList, BaseActivity activity) {
        this.mList = mList;
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return mList!=null? mList.size():0;
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
            convertView = inflater.inflate(R.layout.adapter_coupons,null);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
        holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
        CouponsItem.Coupon item =  mList.get(position);
        holder.tv_price.setText(item.special_price);
        holder.tv_name.setText(item.coupons_name);
        String[] start =  item.start_date.split(" ");
        String[] end =  item.end_date.split(" ");
        holder.tv_time.setText(start[0]+"~"+end[0]);
        return convertView;
    }

    private class ViewHolder{
        private TextView tv_price;
        private TextView tv_name;
        private TextView tv_time;
    }

}
