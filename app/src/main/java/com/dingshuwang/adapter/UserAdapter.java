package com.dingshuwang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dingshuwang.AddressListActivity;
import com.dingshuwang.AllOrderActivity;
import com.dingshuwang.CollectListActivity;
import com.dingshuwang.ConnectUsActivity;
import com.dingshuwang.CouponsActivity;
import com.dingshuwang.MyPublishActivity;
import com.dingshuwang.PointActivity;
import com.dingshuwang.R;
import com.dingshuwang.ShopCartActivity;
import com.dingshuwang.WebViewAct;
import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.util.UIUtil;

/**
 * Created by tx on 2017/6/21.
 */

public class UserAdapter extends BaseAdapter {

    private int[] iv_id;
    private String[] name;
    private BaseActivity activity;
    private LayoutInflater inflater;

    public UserAdapter(int[] iv_id, String[] name, BaseActivity activity) {
        this.iv_id = iv_id;
        this.name = name;
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return iv_id.length;
    }

    @Override
    public Object getItem(int position) {
        return iv_id[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(null == convertView){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_user,null);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.iv_logo = (ImageView) convertView.findViewById(R.id.iv_logo);
        holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        holder.iv_logo.setImageResource(iv_id[position]);
        holder.tv_name.setText(name[position]);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UIUtil.isfastdoubleClick()){
                    return;
                }
                switch (position)
                {
                    case 0://我的订单
                        AllOrderActivity.actToPay(activity);
                        break;
                    case 1: //优惠券
                        CouponsActivity.actionCoupons(activity);
                        break;
                    case 2: //我的积分
                        PointActivity.actPoint(activity);
                        break;
                    case 3://购物车
                        ShopCartActivity.actShop(activity);
                        break;
                    case 4: //我的收藏
                        CollectListActivity.actCollectList(activity);
                        break;
                    case 5: //我的发布
                        MyPublishActivity.actMyPublish(activity);
                        break;
                    case 6://收货地址
                        AddressListActivity.actionAddresList(activity,1);
                        break;
                    case 7://图书回收
                        WebViewAct.actionWebView(activity,"图书回收","http://m.iisbn.com/rec.html");
                        break;
                    case 8: //联系我们
                        ConnectUsActivity.actConnectUs(activity);
                        break;
                }

            }
        });



        return convertView;
    }

    private class ViewHolder{
        private ImageView iv_logo;
        private TextView tv_name;
    }
}
