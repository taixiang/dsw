package com.dingshuwang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dingshuwang.DetailActivity;
import com.dingshuwang.R;
import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.model.FindItem;
import com.dingshuwang.model.NovelItem;
import com.dingshuwang.model.SearchItem;
import com.dingshuwang.util.GlideImgManager;
import com.dingshuwang.util.UIUtil;

import java.util.List;

/**
 * Created by tx on 2017/6/15.
 * 小说专区
 */

public class NovelAdapter extends BaseAdapter {
    private List<NovelItem.Novel> mList;
    private BaseActivity mActivity;
    private LayoutInflater mLayoutInflater;

    public NovelAdapter(List<NovelItem.Novel> mList, BaseActivity mActivity) {
        this.mList = mList;
        this.mActivity = mActivity;
        this.mLayoutInflater = LayoutInflater.from(mActivity);
    }

    @Override
    public int getCount() {
        return mList == null ? 0:mList.size();
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
            convertView = mLayoutInflater.inflate(R.layout.adapter_keywords,null);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.iv_logo = (ImageView) convertView.findViewById(R.id.iv_logo);
        holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        holder.tv_isbn = (TextView) convertView.findViewById(R.id.tv_isbn);
        holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
        holder.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
        holder.tv_level = (TextView) convertView.findViewById(R.id.tv_level);
        holder.tv_discount = (TextView) convertView.findViewById(R.id.tv_discount);

        final NovelItem.Novel item = mList.get(position);
        if(item != null){
            GlideImgManager.loadImage(mActivity,item.img_url,holder.iv_logo);
            holder.tv_name.setText(item.name);
            holder.tv_isbn.setText(item.ISBN);
            holder.tv_price.setText("￥"+item.price_sell);
            holder.tv_count.setText(item.goods_nums);
            holder.tv_level.setText("好");
            holder.tv_discount.setText("旧书代发/批发1.9折起");
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(UIUtil.isfastdoubleClick()){
                        return;
                    }
                    DetailActivity.actionDetail(mActivity,item.id);
                }
            });
        }
        return convertView;
    }

    private class ViewHolder {
        public ImageView iv_logo;
        public TextView tv_name;
        public TextView tv_isbn;
        public TextView tv_price;
        public TextView tv_count;
        public TextView tv_level;
        public TextView tv_discount;
    }
}
