package com.dingshuwang.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dingshuwang.APIURL;
import com.dingshuwang.Constants;
import com.dingshuwang.DataView;
import com.dingshuwang.DetailActivity;
import com.dingshuwang.LoginActivity;
import com.dingshuwang.R;
import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.base.MMApplication;
import com.dingshuwang.model.AddCartItem;
import com.dingshuwang.model.HomeFreeItem;
import com.dingshuwang.model.SearchItem;
import com.dingshuwang.util.GlideImgManager;
import com.dingshuwang.util.GsonUtils;
import com.dingshuwang.util.RequestUtils;
import com.dingshuwang.util.UIUtil;

import java.util.List;

/**
 * Created by tx on 2017/6/9.
 */

public class KeywordsAdapter extends BaseAdapter implements DataView{

    private List<SearchItem.Search> mList;
    private BaseActivity mActivity;
    private LayoutInflater mLayoutInflater;

    private static final String ADD_CART_TAG = "ADD_CART_TAG";


    public KeywordsAdapter(List<SearchItem.Search> mList, BaseActivity mActivity ) {
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
        holder.ll_cart = (LinearLayout) convertView.findViewById(R.id.ll_cart);

        final SearchItem.Search item = mList.get(position);
        if(item != null){
            GlideImgManager.loadImage(mActivity,item.img_url,holder.iv_logo);
            holder.tv_name.setText(item.name);
            if(item.ISBN.contains("销售")){
                String[] isbn = item.ISBN.split("销售");
                holder.tv_isbn.setText(isbn[0]);
            }else {
                holder.tv_isbn.setText(item.ISBN);
            }
            holder.tv_price.setText("￥"+item.price_sell);
            holder.tv_count.setText(item.goods_nums);
            holder.tv_level.setText("好");
            holder.tv_discount.setText("旧书代发/批发1.9折起");
            holder.ll_cart.setVisibility(View.VISIBLE);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(UIUtil.isfastdoubleClick()){
                        return;
                    }
                    DetailActivity.actionDetail(mActivity,item.id);
                }
            });
            holder.ll_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(UIUtil.isfastdoubleClick()){
                        return;
                    }
                    if(MMApplication.mIsLogin){
                        doAddCart(item.id);
                    }else {
                        LoginActivity.actionLogin(mActivity,Constants.LOGIN);
                    }
                }
            });
        }

        return convertView;
    }

    //加入购物车
    private void doAddCart(String id){
        String url = String.format(APIURL.CART_ADD_URL, Constants.USER_ID,id,1);
        RequestUtils.getDataFromUrlByPost(mActivity,url,"",this,ADD_CART_TAG,false,false);
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag) {

    }

    @Override
    public void onGetDataSuccess(String result, String requestTag) {

        if(result !=null){
            AddCartItem item = GsonUtils.jsonToClass(result,AddCartItem.class);
            if("true".equals(item.result)){
                mActivity.showToast("加入购物车成功");
            }else {
                mActivity.showToast(item.msg);
            }
        }
    }

    private class ViewHolder {
        public ImageView iv_logo;
        public TextView tv_name;
        public TextView tv_isbn;
        public TextView tv_price;
        public TextView tv_count;
        public TextView tv_level;
        public TextView tv_discount;
        public LinearLayout ll_cart;

    }

}
