package com.dingshuwang;

import android.content.Intent;

import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.BaseTitleActivity;
import com.dingshuwang.fragment.RegisterFragment;

/**
 * Created by tx on 2017/6/8.
 */

public class RegisterActivity extends BaseTitleActivity {


    public static void actionRegister(BaseActivity activity){
        Intent intent = new Intent(activity,RegisterActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public CharSequence getActivityTitle() {
        return "注册";
    }

    @Override
    public BaseFragment fragmentAsView() {
        return RegisterFragment.newInstance();
    }
}
