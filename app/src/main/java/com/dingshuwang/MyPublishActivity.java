package com.dingshuwang;

import android.content.Intent;

import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.BaseTitleActivity;
import com.dingshuwang.fragment.MyPublishFragment;

/**
 * Created by tx on 2017/7/7.
 */

public class MyPublishActivity extends BaseTitleActivity {

    public static void actMyPublish(BaseActivity activity){
        Intent intent = new Intent(activity,MyPublishActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public CharSequence getActivityTitle() {
        return "我的发布";
    }

    @Override
    public BaseFragment fragmentAsView() {
        return MyPublishFragment.newInstance();
    }
}
