package com.dingshuwang;

import android.content.Intent;

import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.BaseTitleActivity;
import com.dingshuwang.fragment.PayFragment;

/**
 * Created by tx on 2017/7/5.
 */

public class PayActivity extends BaseTitleActivity {

    public static void actConfirm(BaseActivity activity, String order_id){
        Intent intent = new Intent(activity,PayActivity.class);
        intent.putExtra("order_id",order_id);
        activity.startActivity(intent);
    }
    @Override
    public CharSequence getActivityTitle() {
        return "订单详情";
    }

    @Override
    public BaseFragment fragmentAsView() {
        String order_id = getIntent().getStringExtra("order_id");
        return PayFragment.newInstance(order_id);
    }
}
