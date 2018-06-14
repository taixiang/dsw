package com.dingshuwang.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dingshuwang.DetailActivity;
import com.dingshuwang.R;
import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.model.HomeFreeItem;
import com.dingshuwang.util.GlideImgManager;
import com.dingshuwang.util.UIUtil;

import java.util.List;

/**
 * Created by tx on 2017/6/7.
 */

public class FreeAdapter extends BaseAdapter {

    private List<HomeFreeItem.HomeFree> mList;
    private BaseActivity mActivity;
    private LayoutInflater mLayoutInflater;

    public FreeAdapter(List<HomeFreeItem.HomeFree> mList, BaseActivity mActivity ) {
        this.mList = mList;
        this.mActivity = mActivity;
        this.mLayoutInflater = LayoutInflater.from(mActivity);
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
            convertView = mLayoutInflater.inflate(R.layout.adapter_free,null);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.iv_logo = (ImageView) convertView.findViewById(R.id.ic_logo);
        holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);

        final HomeFreeItem.HomeFree item  = mList.get(position);
        if(item != null){
            GlideImgManager.loadImage(mActivity,item.img_url,holder.iv_logo);
            holder.tv_name.setText(item.name);
            holder.tv_price.setText("原价:"+item.price_market);
            holder.tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
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

    private static class ViewHolder{
        private ImageView iv_logo;
        private TextView tv_name;
        private TextView tv_price;


    }


}
