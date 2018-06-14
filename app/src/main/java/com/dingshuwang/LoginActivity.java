package com.dingshuwang;

import android.content.Intent;
import android.util.Log;

import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.BaseTitleActivity;
import com.dingshuwang.fragment.LoginFragment;
import com.dingshuwang.fragment.MainFragment;
import com.dingshuwang.interfaceFile.GoHomeListener;

/**
 * 登录
 */
public class LoginActivity extends BaseTitleActivity {


    public static void actionLogin(BaseActivity activity,int tag){
        Intent intent = new Intent(activity,LoginActivity.class);
        intent.putExtra("tag",tag);
        activity.startActivity(intent);
    }


    @Override
    public BaseFragment fragmentAsView() {
       int tag = getIntent().getIntExtra("tag",0);
        if(tag != 0){
            return LoginFragment.newInstance(tag);
        }
        return LoginFragment.newInstance();
    }



    @Override
    public CharSequence getActivityTitle() {
        return "登录";
    }
}
