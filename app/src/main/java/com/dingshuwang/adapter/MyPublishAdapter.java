package com.dingshuwang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dingshuwang.R;
import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.model.AddressListItem;
import com.dingshuwang.model.PublishListItem;
import com.dingshuwang.util.GlideImgManager;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.util.List;

/**
 * Created by tx on 2017/7/7.
 */

public class MyPublishAdapter extends BaseAdapter {

    private BaseActivity activity;
    private List<PublishListItem.Publish> list;
    private LayoutInflater inflater;

    public MyPublishAdapter(BaseActivity activity, List<PublishListItem.Publish> list) {
        this.activity = activity;
        this.list = list;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return list==null ? 0 : list.size();
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
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_my_publish,null);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
        holder.iv_logo = (CircularImageView) convertView.findViewById(R.id.iv_logo);
        holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        holder.tv_isbn = (TextView) convertView.findViewById(R.id.tv_isbn);
        holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
        PublishListItem.Publish item = list.get(position);
        if(item != null){
            holder.tv_time.setText(item.create_date);
            GlideImgManager.loadImage(activity,item.image_url,holder.iv_logo);
            holder.tv_name.setText(item.pro_name);
            holder.tv_isbn.setText("ISBN："+item.pro_isbn);
            holder.tv_price.setText("价格：￥"+item.price_sell);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }

    private class ViewHolder{
        private TextView tv_time;
        private CircularImageView iv_logo;
        private TextView tv_name;
        private TextView tv_isbn;
        private TextView tv_price;
    }

}
