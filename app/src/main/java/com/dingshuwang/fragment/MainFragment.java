package com.dingshuwang.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;

import com.dingshuwang.Constants;
import com.dingshuwang.IsbnCodeActivity;
import com.dingshuwang.LoginActivity;
import com.dingshuwang.R;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.MMApplication;
import com.dingshuwang.interfaceFile.GoHomeListener;
import com.dingshuwang.util.PhotoUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by tx on 2017/6/6.
 */

public class MainFragment extends BaseFragment {

    private static final String TAB_HOME = "首页";
    private static final String TAB_FIND = "发现";
    private static final String TAB_PUBLISH = "发布";
    private static final String TAB_SHOPPINGCART = "购物车";
    private static final String TAB_ME = "我的";
    public static final String TAB_PURCHASE = "扫码ISBN";

    private boolean isPublish;
    private boolean isShoppingCart;
    private boolean isUser;


    private FragmentTabHost mFragmentTabHost;

    public static MainFragment newInstance()
    {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        initView(view);

        final ImageView imageView = (ImageView) view.findViewById(R.id.image);
        imageView.setImageResource(R.mipmap.welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imageView.setVisibility(View.GONE);
            }
        },3000);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void initView(View view)
    {
        mFragmentTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
        setUpFragmentTabHost();
    }

    private void setUpFragmentTabHost()
    {
        mFragmentTabHost.setup(mActivity, mActivity.getSupportFragmentManager(), R.id.frame_real_tab_container);
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        //=================================================
        View viewSelect = inflater.inflate(R.layout.layout_home_tab_item, null);
        final Button btnSelect = (Button) viewSelect.findViewById(R.id.btn_tab);
        final ImageView ivSelect = (ImageView) viewSelect.findViewById(R.id.iv_tab);
        ivSelect.setImageResource(R.drawable.activated_home_tab_select_background);
        ivSelect.setActivated(true);
        btnSelect.setActivated(true);
        btnSelect.setText(TAB_HOME);

        //发现
        View viewFind = inflater.inflate(R.layout.layout_home_tab_item, null);
        final Button btnFind = (Button) viewFind.findViewById(R.id.btn_tab);
        final ImageView ivFind = (ImageView) viewFind.findViewById(R.id.iv_tab);
        ivFind.setImageResource(R.drawable.activated_home_tab_find_background);
        btnFind.setText(TAB_FIND);

        //================================================= 发布
        View viewPublish = inflater.inflate(R.layout.layout_home_tab_item, null);
        final Button btnPublish = (Button) viewPublish.findViewById(R.id.btn_tab);
        final ImageView ivPublish = (ImageView) viewPublish.findViewById(R.id.iv_tab);
        ivPublish.setImageResource(R.drawable.activated_home_tab_publish_background);
        btnPublish.setText(TAB_PUBLISH);
        //=================================================
        View viewShop = inflater.inflate(R.layout.layout_home_tab_item, null);
        final Button btnShoppingCart = (Button) viewShop.findViewById(R.id.btn_tab);
        final ImageView ivShoppingCart = (ImageView) viewShop.findViewById(R.id.iv_tab);
        ivShoppingCart.setImageResource(R.drawable.activated_home_tab_shopping_cart_background);
        btnShoppingCart.setText(TAB_SHOPPINGCART);
        //=================================================
        View viewUser = inflater.inflate(R.layout.layout_home_tab_item, null);
        final Button btnUser = (Button) viewUser.findViewById(R.id.btn_tab);
        final ImageView ivUser = (ImageView) viewUser.findViewById(R.id.iv_tab);
        ivUser.setImageResource(R.drawable.activated_home_tab_user_background);
        btnUser.setText(TAB_ME);
        //=================================================

        View viewPurchase = inflater.inflate(R.layout.layout_home_tab_item, null);
        final Button btnPurchase = (Button) viewPurchase.findViewById(R.id.btn_tab);
        final ImageView ivPurchase = (ImageView) viewPurchase.findViewById(R.id.iv_tab);
        ivPurchase.setImageResource(R.drawable.activated_home_tab_goods_background);
        btnPurchase.setText(TAB_PURCHASE);

        mFragmentTabHost.addTab(mFragmentTabHost.newTabSpec(btnSelect.getText().toString()).setIndicator(viewSelect), HomeFragment.class, null);
        mFragmentTabHost.addTab(mFragmentTabHost.newTabSpec(btnFind.getText().toString()).setIndicator(viewFind),FindFragment.class,null);
        mFragmentTabHost.addTab(mFragmentTabHost.newTabSpec(btnPublish.getText().toString()).setIndicator(viewPublish), PublishFragment.class, null);
        mFragmentTabHost.addTab(mFragmentTabHost.newTabSpec(btnShoppingCart.getText().toString()).setIndicator(viewShop), ShoppingCartFragment.class, null);
        mFragmentTabHost.addTab(mFragmentTabHost.newTabSpec(btnUser.getText().toString()).setIndicator(viewUser), UserCenterFragment.class, null);
        mFragmentTabHost.addTab(mFragmentTabHost.newTabSpec(btnPurchase.getText().toString()).setIndicator(viewPurchase), ScanCodeFragment.class, null);

        mFragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener()
        {
            public void onTabChanged(String tabId)
            {
                btnSelect.setActivated(tabId.equals(btnSelect.getText()));
                ivSelect.setActivated(tabId.equals(btnSelect.getText()));

                btnFind.setActivated(tabId.equals(btnFind.getText()));
                ivFind.setActivated(tabId.equals(btnFind.getText()));

                isPublish = tabId.equals(btnPublish.getText());
                btnPublish.setActivated(isPublish);
                ivPublish.setActivated(isPublish);

                isShoppingCart = tabId.equals(btnShoppingCart.getText());
                btnShoppingCart.setActivated(isShoppingCart);
                ivShoppingCart.setActivated(isShoppingCart);

                isUser = tabId.equals(btnUser.getText());
                btnUser.setActivated(isUser);
                ivUser.setActivated(isUser);

                boolean isPurchase = tabId.equals(btnPurchase.getText());
                btnPurchase.setActivated(isPurchase);
                ivPurchase.setActivated(isPurchase);

//                if (isUser || isGoods||isShoppingCart)
//                {
//                    mActivity.hideTitleBar();
//                } else
//                {
//                    mActivity.showTitleBar();
//                }
                mActivity.hideTitleBar();
                if(isPublish&& !MMApplication.mIsLogin){
                    toFirstTab(1);
                }
                if(isShoppingCart&& !MMApplication.mIsLogin){
                    toFirstTab(2);
                }
                if(isUser&& !MMApplication.mIsLogin){
                    toFirstTab(3);
                }
//                if(isPurchase && !MMApplication.mIsLogin){
//                    loadNext(LoginActivity.class);
//                }
            }
        });
    }

    public void toTab(int index)
    {
        if(null != mFragmentTabHost){
            mFragmentTabHost.setCurrentTab(index);
        }
    }

    private void toFirstTab(int requestCode){
        Intent intent = new Intent(mActivity, LoginActivity.class);
        mActivity.startActivityForResult(intent , requestCode);
        mActivity.overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("》》》》》 main","requestCode ==== "+requestCode+"");
        Log.i("》》》》》 main","resultcode ==== "+resultCode+"");

        if(resultCode == Constants.UNLOGIN){  //未登录，发布、购物车等页面返回到首页
            if(requestCode == 1){
                toTab(2);
            }else if(requestCode == 2){
                toTab(3);
            }else if(requestCode ==3){
                toTab(4);
            }
        }else if(requestCode == Constants.CODE_HOME){ //首页扫描后跳列表页
            if(resultCode == Constants.CAMMER){
                Bundle bundle = data.getExtras();
                String scanResult = bundle.getString("result");
                Log.i("》》》》》  "," scanresult === "+scanResult);
                if(scanResult != null){
                    IsbnCodeActivity.actIsbn(mActivity,scanResult);
                }
            }
        } else if(requestCode == PhotoUtils.REQUEST_FROM_CAMERA || PhotoUtils.REQUEST_FROM_PHOTO == requestCode){ //发布添加封面
            toTab(2);
        }else if(resultCode == Constants.CAMMER_PUBLISH){ //发布扫描后返回
            toTab(2);
        }else if(resultCode == Constants.CAMMER_PURCHASE){ //采购返回
//            toTab(5);
        }else if(requestCode == PhotoUtils.REQUEST_FROM_CAMERA_2 || PhotoUtils.REQUEST_FROM_PHOTO_2 == requestCode){ //发布添加封面
//            toTab(5);
        }else if(requestCode == Constants.CODE_ISBN){ //专业扫描isbn
            toTab(5);
        }else{
            if(!MMApplication.mIsLogin){
                toTab(0);
            }
//            toTab(0);
        }
    }

    @Override
    public String getFragmentTitle() {
        return null;
    }

}
