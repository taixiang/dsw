package com.dingshuwang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dingshuwang.R;
import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.model.CommentItem;

import java.util.List;

/**
 * Created by Administrator on 2016/8/20.
 */
public class CommentAdapter extends BaseAdapter {

    private List<CommentItem.Comment> list;
    private BaseActivity activity;
    private LayoutInflater inflater;

    public CommentAdapter(List<CommentItem.Comment> list, BaseActivity activity) {
        this.list = list;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }


    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
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
            convertView = inflater.inflate(R.layout.comment_item_view,null);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_mark = (TextView) convertView.findViewById(R.id.tv_content);
        holder.tv_point = (TextView) convertView.findViewById(R.id.tv_time);
        if(list !=null){
            holder.tv_mark.setText(list.get(position).comment_content);
            holder.tv_point.setText(list.get(position).add_time);
        }
        return convertView;
    }

    private class ViewHolder{
        private TextView tv_point;
        private TextView tv_mark;
    }

}
