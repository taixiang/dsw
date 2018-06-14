package com.dingshuwang;

import android.content.Intent;

import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.BaseTitleActivity;
import com.dingshuwang.fragment.CouponsFragment;

/**
 * Created by tx on 2017/6/21.
 */

public class CouponsActivity extends BaseTitleActivity {

    public static void actionCoupons(BaseActivity activity){
        Intent intent = new Intent(activity , CouponsActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public CharSequence getActivityTitle() {
        return "我的优惠券";
    }

    @Override
    public BaseFragment fragmentAsView() {
        return CouponsFragment.newInstance();
    }
}
