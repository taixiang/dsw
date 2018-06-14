package com.dingshuwang.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.dingshuwang.APIURL;
import com.dingshuwang.ConfirmActivity;
import com.dingshuwang.Constants;
import com.dingshuwang.DataView;
import com.dingshuwang.LoginActivity;
import com.dingshuwang.R;
import com.dingshuwang.adapter.ShopcartAdapter;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.MMApplication;
import com.dingshuwang.model.ShopCartItem;
import com.dingshuwang.util.GsonUtils;
import com.dingshuwang.util.RequestUtils;
import com.dingshuwang.util.UIUtil;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by tx on 2017/6/6.
 */

public class ShoppingCartFragment extends BaseFragment implements DataView{

    public static final String GOODS_LIST = "GOODS_LIST";

    @Bind(R.id.listview)
    ListView listView;

    @Bind(R.id.tv_money)
    TextView tv_money;

    @Bind(R.id.tv_empty)
    TextView tv_empty;

    private ShopcartAdapter adapter;
    private ShopCartItem cartItem;
    private String allMoney;

    public static ShoppingCartFragment newInstance(){
        return new ShoppingCartFragment();
    }

    private void doGetShop(){
        String url = String.format(APIURL.GOODS_LIST, Constants.USER_ID);
        RequestUtils.getDataFromUrl(mActivity,url,this,GOODS_LIST,false,false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shopcart,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        register();
        adapter = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        doGetShop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(receiver != null){
            mActivity.unregisterReceiver(receiver);

        }
    }

    @OnClick(R.id.confirm)
    public void confirm(){
        if(UIUtil.isfastdoubleClick()){
            return;
        }
        if(!MMApplication.mIsLogin){
            LoginActivity.actionLogin(mActivity,Constants.LOGIN);
        }else {
            if(cartItem != null && cartItem.shops != null && cartItem.shops.size()>0){
                ConfirmActivity.actConfirm(mActivity, GsonUtils.toJson(cartItem), tv_money.getText().toString().trim());

            }
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Constants.GOOD_NUM)){
                doCount(intent.getStringExtra("book_id"),Integer.parseInt(intent.getStringExtra("count")));
            }else if(intent.getAction().equals(Constants.DELETE_GOOD) ){
                doGetShop();
            }else if(intent.getAction().equals(Constants.LOGIN_SUCCESS)){
//                doGetShop();
            }
        }
    };

    private void doCount(String pro_id,int num){
        if(cartItem != null && cartItem.shops != null){
            for(ShopCartItem.ShopsItem item : cartItem.shops){
                for(ShopCartItem.ShopItem shopItem :item.Shop){
                    if(pro_id.equals(shopItem.pro_id)){
                        shopItem.order_number = num+"";
                    }
                }
            }
            double allPrice=0;
            double all=0;
            double price_cell;
            int count;
            for(ShopCartItem.ShopsItem item : cartItem.shops){
                for(ShopCartItem.ShopItem shopItem : item.Shop){
                    price_cell= Double.parseDouble(shopItem.price_sell);
                    count = Integer.parseInt(shopItem.order_number);
                    allPrice = price_cell * count;
                    all+=allPrice;
                }
            }
            String money =  new DecimalFormat("#0.00").format(all);
            tv_money.setText(money);
        }

    }

    private void register(){

        IntentFilter filter_count = new IntentFilter(Constants.GOOD_NUM);
        mActivity.registerReceiver(receiver,filter_count);

        IntentFilter filter_delete = new IntentFilter(Constants.DELETE_GOOD);
        mActivity.registerReceiver(receiver,filter_delete);

        IntentFilter filter_login = new IntentFilter(Constants.LOGIN_SUCCESS);
        mActivity.registerReceiver(receiver,filter_login);
    }

    @Override
    public String getFragmentTitle() {
        return null;
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag) {

    }

    @Override
    public void onGetDataSuccess(String result, String requestTag) {
        if(result != null){
            if(GOODS_LIST.equals(requestTag)){
                cartItem = GsonUtils.jsonToClass(result,ShopCartItem.class);

                if(null != cartItem && null != cartItem.shops){
//                    if(null == adapter){
                    Log.i("》》》》   "," cartItem  "+cartItem.toString());
                    tv_empty.setVisibility(View.GONE);
                    adapter = new ShopcartAdapter(mActivity,cartItem.shops);
                    listView.setAdapter(adapter);
//                    }else {
//                        adapter.notifyDataSetChanged();
//                    }
                    double allPrice=0;
                    double all=0;
                    double price_cell;
                    int count;
                    for(ShopCartItem.ShopsItem item : cartItem.shops){
                        for(ShopCartItem.ShopItem shopItem : item.Shop){
                            price_cell= Double.parseDouble(shopItem.price_sell);
                            count = Integer.parseInt(shopItem.order_number);
                            allPrice = price_cell * count;
                            all+=allPrice;
                        }
                    }
                    allMoney =  new DecimalFormat("#0.00").format(all);
                    tv_money.setText(allMoney);
                    tv_money.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.VISIBLE);
                }else {
                    tv_empty.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                    tv_money.setVisibility(View.INVISIBLE);
                    Log.i("》》》》  "," 222222 ");
                    if(adapter != null && cartItem != null && cartItem.shops != null){
                        cartItem.shops.clear();
                        Log.i("》》》》  "," 33333 ");
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }

    }
}
