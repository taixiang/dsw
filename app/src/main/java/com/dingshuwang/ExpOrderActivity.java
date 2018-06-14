package com.dingshuwang;

import android.content.Intent;

import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.BaseTitleActivity;
import com.dingshuwang.fragment.ExpOrderFragment;

/**
 * Created by Administrator on 2016/7/28.
 */
public class ExpOrderActivity extends BaseTitleActivity {

    public static void actExpOrder(BaseActivity activity){
        Intent intent = new Intent(activity,ExpOrderActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public BaseFragment fragmentAsView() {
        return ExpOrderFragment.newInstance();
    }

    @Override
    public CharSequence getActivityTitle() {
        return "待发货";
    }
}
