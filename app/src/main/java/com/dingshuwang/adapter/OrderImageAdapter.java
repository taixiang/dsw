package com.dingshuwang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.dingshuwang.R;
import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.model.ShopCartItem;
import com.dingshuwang.util.GlideImgManager;

import java.util.List;

/**
 * Created by tx on 2017/6/19.
 */

public class OrderImageAdapter extends BaseAdapter {

    private BaseActivity activity;
    private LayoutInflater mLayoutInflater;
    private List<String> mList;


    public OrderImageAdapter(BaseActivity activity, List<String> mList) {
        this.activity = activity;
        this.mList = mList;
        mLayoutInflater = LayoutInflater.from(activity);
    }
    @Override
    public int getCount() {
        return mList== null ? 0:mList.size();
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
            convertView = mLayoutInflater.inflate(R.layout.adapter_order_image,null);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imageView = (ImageView) convertView.findViewById(R.id.image);
        GlideImgManager.loadImage(activity,mList.get(position),holder.imageView);

        return convertView;
    }
    private class ViewHolder{
        private ImageView imageView;
    }
}
