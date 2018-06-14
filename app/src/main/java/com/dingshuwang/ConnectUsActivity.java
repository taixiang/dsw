package com.dingshuwang;

import android.content.Intent;

import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.BaseTitleActivity;
import com.dingshuwang.fragment.ConnectUsFragment;

/**
 * Created by tx on 2017/6/28.
 */

public class ConnectUsActivity extends BaseTitleActivity {

    public static void actConnectUs(BaseActivity activity){
        Intent intent = new Intent(activity,ConnectUsActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public CharSequence getActivityTitle() {
        return "联系我们";
    }

    @Override
    public BaseFragment fragmentAsView() {
        return ConnectUsFragment.newInstance();
    }
}
