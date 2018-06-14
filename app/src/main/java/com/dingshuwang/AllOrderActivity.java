package com.dingshuwang;

import android.content.Intent;

import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.BaseTitleActivity;
import com.dingshuwang.fragment.AllOrderFragment;

/**
 * Created by tx on 2017/7/6.
 */

public class AllOrderActivity extends BaseTitleActivity {

    public static void actToPay(BaseActivity activity){
        Intent intent = new Intent(activity,AllOrderActivity.class);
        activity.startActivity(intent);
    }


    @Override
    public CharSequence getActivityTitle() {
        return "全部订单";
    }

    @Override
    public BaseFragment fragmentAsView() {
        return AllOrderFragment.newInstance();
    }
}
