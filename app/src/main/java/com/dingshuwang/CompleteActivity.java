package com.dingshuwang;

import android.content.Intent;

import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.BaseTitleActivity;
import com.dingshuwang.fragment.CompleteFragment;

/**
 * Created by Administrator on 2016/7/29.
 */
public class CompleteActivity extends BaseTitleActivity {

    public static void actComplete(BaseActivity activity){
        Intent intent = new Intent(activity,CompleteActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public BaseFragment fragmentAsView() {
        return CompleteFragment.newInstance();
    }

    @Override
    public CharSequence getActivityTitle() {
        return "已完成";
    }
}
