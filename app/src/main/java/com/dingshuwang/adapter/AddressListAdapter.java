package com.dingshuwang.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dingshuwang.APIURL;
import com.dingshuwang.AddressEditActivity;
import com.dingshuwang.Constants;
import com.dingshuwang.DataView;
import com.dingshuwang.R;
import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.model.AddressListItem;
import com.dingshuwang.util.RequestUtils;
import com.dingshuwang.util.UIUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/7/21.
 */
public class AddressListAdapter extends BaseAdapter {

    private BaseActivity activity;
    private List<AddressListItem.AddressItem> list;
    private LayoutInflater inflater;
    private int tag;

    public AddressListAdapter(BaseActivity activity, List<AddressListItem.AddressItem> list,int tag) {
        this.activity = activity;
        this.list = list;
        inflater = LayoutInflater.from(activity);
        this.tag = tag;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_address_list,null);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_name = (TextView) convertView.findViewById(R.id.name);
        holder.tv_phone = (TextView) convertView.findViewById(R.id.phone);
        holder.tv_address = (TextView) convertView.findViewById(R.id.address);
        holder.iv_edit = (ImageView) convertView.findViewById(R.id.iv_edit);
        holder.tv_name.setText(list.get(position).accept_name);
        holder.tv_phone.setText(list.get(position).phone);
        holder.tv_address.setText(list.get(position).area+list.get(position).address);
        holder.iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UIUtil.isfastdoubleClick()){
                    return;
                }
                AddressEditActivity.actionAddressEdit(activity,list.get(position).id);
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tag == 1){
                    return;
                }
                DataView dataView = new DataView() {
                    @Override
                    public void onGetDataFailured(String msg, String requestTag) {

                    }

                    @Override
                    public void onGetDataSuccess(String result, String requestTag) {
//                        Intent intent = new Intent(Constants.DEFAULT_ADDRESS);
//                        activity.sendBroadcast(intent);
                        if(result != null){
                            activity.finish();
                        }
                    }
                };
                String url = String.format(APIURL.DEFAULT_ADDRESS_URL, Constants.USER_ID,list.get(position).id);
                RequestUtils.getDataFromUrl(activity,url,dataView,"",false,false);
            }
        });
        return convertView;
    }

    public static class ViewHolder{
        private TextView tv_name;
        private TextView tv_phone;
        private TextView tv_address;
        private ImageView iv_edit;
    }

}
