package com.dingshuwang;

import android.content.Intent;

import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.BaseTitleActivity;
import com.dingshuwang.fragment.ShoppingCartFragment;

public class ShopCartActivity extends BaseTitleActivity {

    public static void actShop(BaseActivity activity){
        Intent intent = new Intent(activity,ShopCartActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public BaseFragment fragmentAsView() {
        return ShoppingCartFragment.newInstance();
    }

    @Override
    public CharSequence getActivityTitle() {
        return "购物车";
    }
}
