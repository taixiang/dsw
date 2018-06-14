package com.dingshuwang.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dingshuwang.KeywordsListActivity;
import com.dingshuwang.NovelActivity;
import com.dingshuwang.R;
import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.model.HomeMiddleItem;
import com.dingshuwang.util.GlideImgManager;
import com.dingshuwang.util.UIUtil;

import java.util.List;

/**
 * Created by tx on 2017/6/7.
 */

public class ColumnAdapter extends BaseAdapter {

    private List<HomeMiddleItem.HomeMiddle> mList;
    private BaseActivity mActivity;
    private LayoutInflater mLayoutInflater;

    public ColumnAdapter(List<HomeMiddleItem.HomeMiddle> mList, BaseActivity mActivity) {
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
            convertView = mLayoutInflater.inflate(R.layout.adapter_column,null);
            holder = new ViewHolder();
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.iv_logo = (ImageView) convertView.findViewById(R.id.iv_logo);
        holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        final HomeMiddleItem.HomeMiddle item = mList.get(position);
        if(item != null && item.keywords != null && item.image_url != null){

            holder.tv_name.setText(item.keywords);
            GlideImgManager.loadImage(mActivity,item.image_url,holder.iv_logo);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UIUtil.isfastdoubleClick()){
                    return;
                }
                if("小说专区".equals(item.keywords)){
                    NovelActivity.actionNovel(mActivity);
                }else {
                    KeywordsListActivity.actionKeywordsList(mActivity,item.keywords);
                }
            }
        });


        return convertView;
    }


    public static class ViewHolder {
        private TextView tv_name;
        private ImageView iv_logo;

    }


}
