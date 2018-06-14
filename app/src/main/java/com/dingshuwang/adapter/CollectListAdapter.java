package com.dingshuwang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dingshuwang.DetailActivity;
import com.dingshuwang.R;
import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.model.AddressListItem;
import com.dingshuwang.model.CollectListItem;
import com.dingshuwang.util.GlideImgManager;
import com.dingshuwang.util.UIUtil;

import java.util.List;

/**
 * Created by tx on 2017/7/7.
 */

public class CollectListAdapter extends BaseAdapter {
    private BaseActivity activity;
    private List<CollectListItem.CollectList> list;
    private LayoutInflater inflater;

    public CollectListAdapter(BaseActivity activity, List<CollectListItem.CollectList> list) {
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
            convertView = inflater.inflate(R.layout.adapter_collect_list,null);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.iv_logo = (ImageView) convertView.findViewById(R.id.iv_logo);
        holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        holder.tv_isbn = (TextView) convertView.findViewById(R.id.tv_isbn);
        holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
        final CollectListItem.CollectList item =  list.get(position);
        GlideImgManager.loadImage(activity,item.img_url,holder.iv_logo);
        holder.tv_name.setText(item.name);
        holder.tv_isbn.setText(item.ISBN);
//        holder.tv_price.setText(item.pro_id);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UIUtil.isfastdoubleClick()){
                    return;
                }
                DetailActivity.actionDetail(activity,item.pro_id);
            }
        });


        return convertView;
    }

    private class ViewHolder{
        public ImageView iv_logo;
        public TextView tv_name;
        public TextView tv_isbn;
        public TextView tv_price;
    }


}
