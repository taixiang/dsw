package com.dingshuwang;

import android.content.Intent;

import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.BaseTitleActivity;
import com.dingshuwang.fragment.PointFragment;

/**
 * Created by tx on 2017/6/22.
 */

public class PointActivity extends BaseTitleActivity {

    public static void actPoint(BaseActivity activity){
        Intent intent = new Intent(activity,PointActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public CharSequence getActivityTitle() {
        return "积分记录";
    }

    @Override
    public BaseFragment fragmentAsView() {
        return PointFragment.newInstance();
    }
}
