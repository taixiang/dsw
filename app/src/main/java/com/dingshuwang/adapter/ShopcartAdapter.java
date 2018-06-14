package com.dingshuwang.adapter;

import android.content.Intent;
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
import com.dingshuwang.R;
import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.model.DropCartItem;
import com.dingshuwang.model.ShopCartItem;
import com.dingshuwang.util.GlideImgManager;
import com.dingshuwang.util.GsonUtils;
import com.dingshuwang.util.RequestUtils;
import com.dingshuwang.util.UIUtil;
import com.dingshuwang.view.AlertDialogUI;

import java.util.List;

/**
 * Created by Administrator on 2016/7/9.
 */
public class ShopcartAdapter extends BaseAdapter {

    private BaseActivity activity;
    private LayoutInflater mLayoutInflater;
    private List<ShopCartItem.ShopsItem> mList;


    public ShopcartAdapter(BaseActivity activity, List<ShopCartItem.ShopsItem> mList) {
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
        if(convertView == null){
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.adapter_shopcart,null);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_sup_name = (TextView) convertView.findViewById(R.id.sup_name);
        holder.container = (LinearLayout) convertView.findViewById(R.id.container);
        final ShopCartItem.ShopsItem shopsItem = mList.get(position);
        holder.tv_sup_name.setText(shopsItem.supplier_name);
        for(final ShopCartItem.ShopItem shopItem : shopsItem.Shop){

             final int sell_count;
            View view = mLayoutInflater.inflate(R.layout.layout_shop_item,null);
            ImageView iv_logo = (ImageView) view.findViewById(R.id.iv_logo);
            ImageView iv_minus = (ImageView) view.findViewById(R.id.iv_minus);
            ImageView iv_add = (ImageView) view.findViewById(R.id.iv_add);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
            final TextView tv_count = (TextView) view.findViewById(R.id.tv_count);
            ImageView tv_delete = (ImageView) view.findViewById(R.id.tv_delete);
            GlideImgManager.loadImage(activity,shopItem.img_url,iv_logo);
            tv_name.setText(shopItem.pro_name);
            tv_price.setText(shopItem.price_sell);
            tv_count.setText(shopItem.order_number);
            final int[] count = {Integer.parseInt(shopItem.order_number)};
            sell_count = shopItem.available_nums;
            iv_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(count[0] > 1){
                        count[0] -=1;
                        tv_count.setText(count[0] +"");
                        Intent intent = new Intent(Constants.GOOD_NUM);
                        intent.putExtra("book_id",shopItem.pro_id);
                        intent.putExtra("count",tv_count.getText().toString());
                        activity.sendBroadcast(intent);
                        DataView dataView = new DataView() {
                            @Override
                            public void onGetDataFailured(String msg, String requestTag) {

                            }

                            @Override
                            public void onGetDataSuccess(String result, String requestTag) {

                            }
                        };
                        String url = String.format(APIURL.CART_ADD_URL, Constants.USER_ID,shopItem.pro_id,tv_count.getText().toString());
                        RequestUtils.getDataFromUrlByPost(activity,url,"",dataView,"",false,false);
                    }
                }
            });
            iv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count[0] = Integer.parseInt(tv_count.getText().toString());
                    if(count[0] < sell_count){
                        count[0] +=1;
                        tv_count.setText(count[0] +"");
                        Intent intent = new Intent(Constants.GOOD_NUM);
                        intent.putExtra("book_id",shopItem.pro_id);
                        intent.putExtra("count",tv_count.getText().toString());
                        activity.sendBroadcast(intent);

                        DataView dataView = new DataView() {
                            @Override
                            public void onGetDataFailured(String msg, String requestTag) {

                            }

                            @Override
                            public void onGetDataSuccess(String result, String requestTag) {

                            }
                        };
                        String url = String.format(APIURL.CART_ADD_URL, Constants.USER_ID,shopItem.pro_id,tv_count.getText().toString());
                        RequestUtils.getDataFromUrlByPost(activity,url,"",dataView,"",false,false);



                    }else {
                        activity.showToast("库存已达最大");
                    }
                }
            });

            tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(UIUtil.isfastdoubleClick()){
                        return;
                    }
                    final AlertDialogUI ad = new AlertDialogUI(activity);
                    ad.setTitle("提示：");
                    ad.setMessage("您确定要删除此图书吗？");
                    ad.setCanceledOnTouchOutside(true);
                    ad.setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            doDelete(shopItem.pro_id,ad);
                        }
                    });
                    ad.setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                             ad.dismiss();


                        }
                    });
                    ad.showDialog();

                }
            });
            holder.container.addView(view);
        }
        return convertView;
    }

    private void doDelete(String pro_id, final AlertDialogUI ad){
        String url = String.format(APIURL.DELET_GOOD, Constants.USER_ID,pro_id);
        DataView dataView = new DataView() {
            @Override
            public void onGetDataFailured(String msg, String requestTag) {

            }

            @Override
            public void onGetDataSuccess(String result, String requestTag) {
                if(null != result){
                    DropCartItem dropCartItem = GsonUtils.jsonToClass(result,DropCartItem.class);
                    if(dropCartItem.result){
                        if(ad != null){
                            ad.dismiss();
                        }
                        activity.showToast("删除成功");

                        Intent intent = new Intent(Constants.DELETE_GOOD);
                        activity.sendBroadcast(intent);
                    }else if(dropCartItem.msg != null){
                        activity.showToast(dropCartItem.msg);
                    }

                }
            }
        };
        RequestUtils.getDataFromUrlByPost(activity,url,"",dataView,"",false,false);
    }

    public static class ViewHolder{
        public TextView tv_sup_name;
        public LinearLayout container;
    }



}
