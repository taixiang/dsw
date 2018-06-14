package com.dingshuwang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dingshuwang.APIURL;
import com.dingshuwang.Constants;
import com.dingshuwang.DataView;
import com.dingshuwang.PREF;
import com.dingshuwang.R;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.MMApplication;
import com.dingshuwang.model.RegisterItem;
import com.dingshuwang.util.GsonUtils;
import com.dingshuwang.util.RequestUtils;
import com.dingshuwang.util.StringUtils;
import com.dingshuwang.util.UIUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by tx on 2017/6/8.
 */

public class RegisterFragment extends BaseFragment implements DataView{
    @Bind(R.id.et_name)
    EditText et_name;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.et_code)
    EditText et_code;
    @Bind(R.id.et_confirm_password)
    EditText et_confirm_password;

    private static final String REGISTER_URL = "register_url";

    public static RegisterFragment newInstance(){
        return new RegisterFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register,null);
    }

    @OnClick(R.id.tv_register)
    void register(){
        if(UIUtil.isfastdoubleClick()){
            return;
        }

        String name = et_name.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String confirm_password = et_confirm_password.getText().toString().trim();
        if(StringUtils.isEmpty(name)){
            mActivity.showToast("请填写账户");
            return;
        }
        if(StringUtils.isEmpty(password)){
            mActivity.showToast("请填写密码");
            return;
        }
        if(!password.equals(confirm_password)){
            mActivity.showToast("两次输入密码不同");
            return;
        }
        registerUser(name,password);
    }

    @OnClick(R.id.tv_login)
    void login(){
        if(UIUtil.isfastdoubleClick()){
            return;
        }
        mActivity.finish();
    }

    private void registerUser(String name ,String password){
        String url = String.format(APIURL.REGISTER_URL,name,password,Constants.token);
        RequestUtils.getDataFromUrl(mActivity,url,this,REGISTER_URL,false,false);
    }

    @Override
    public String getFragmentTitle() {
        return null;
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag) {
        if(REGISTER_URL.equals(requestTag)){
            mActivity.showToast("注册失败，稍后再试");
        }
    }

    @Override
    public void onGetDataSuccess(String result, String requestTag) {
        if(result != null){
            RegisterItem registerItem = GsonUtils.jsonToClass(result,RegisterItem.class);
            if(registerItem != null){
                if(REGISTER_URL.equals(requestTag)){
                    mActivity.showToast(registerItem.info);
                    if("y".equals(registerItem.status)){
//                        MMApplication.mIsLogin = true;
//                        mActivity.getPreferences().edit().putString(PREF.PREF_USER_ID,registerItem.user_id).apply();
//                        Constants.USER_ID = registerItem.user_id;
//                        Intent intent = new Intent(Constants.LOGIN_FLAG_CLEAR);
//                        mActivity.sendBroadcast(intent);
                        mActivity.finish();
                    }
                }
            }
        }
    }
}