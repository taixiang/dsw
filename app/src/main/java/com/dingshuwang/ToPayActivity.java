package com.dingshuwang;

import android.content.Intent;

import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.BaseTitleActivity;
import com.dingshuwang.fragment.ToPayFragment;

/**
 * Created by Administrator on 2016/7/25.
 */
public class ToPayActivity extends BaseTitleActivity {

    public static void actToPay(BaseActivity activity){
        Intent intent = new Intent(activity,ToPayActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public BaseFragment fragmentAsView() {
        return ToPayFragment.newInstance();
    }

    @Override
    public CharSequence getActivityTitle() {
        return "待付款";
    }
}
