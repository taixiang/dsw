package com.dingshuwang;

import android.content.Intent;

import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.BaseTitleActivity;
import com.dingshuwang.fragment.ForExpFragment;

/**
 * Created by Administrator on 2016/7/28.
 */
public class ForExpActivity extends BaseTitleActivity {

    public static void actForExp(BaseActivity activity){
        Intent intent = new Intent(activity,ForExpActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public BaseFragment fragmentAsView() {
        return ForExpFragment.newInstance();
    }

    @Override
    public CharSequence getActivityTitle() {
        return "待收货";
    }
}
