package com.dingshuwang.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dingshuwang.APIURL;
import com.dingshuwang.Constants;
import com.dingshuwang.DataView;
import com.dingshuwang.PREF;
import com.dingshuwang.R;
import com.dingshuwang.RegisterActivity;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.MMApplication;
import com.dingshuwang.model.LoginItem;
import com.dingshuwang.util.GsonUtils;
import com.dingshuwang.util.RequestUtils;
import com.dingshuwang.util.StringUtils;
import com.dingshuwang.util.UIUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 *
 */
public class LoginFragment extends BaseFragment implements DataView {

    private static final String LOGIN_TAG = "login_tag";

    @Bind(R.id.et_name)
    EditText et_name;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.et_code)
    EditText et_code;

    private int tag;


    public static LoginFragment newInstance(){
        return new LoginFragment();
    }

    public static LoginFragment newInstance(int tag){
        LoginFragment fragment = new LoginFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tag",tag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_login,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getArguments() !=null){
            tag = getArguments().getInt("tag");
        }

    }



    @OnClick(R.id.tv_login)
    void Login(){
        if(UIUtil.isfastdoubleClick()){
            return;
        }

        String name = et_name.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        if(StringUtils.isEmpty(name)){
            mActivity.showToast("请填写账户");
            return;
        }
        if(StringUtils.isEmpty(password)){
            mActivity.showToast("请填写密码");
            return;
        }
        loginUser(name,password);

    }

    @OnClick(R.id.tv_register)
    void register(){
        if(UIUtil.isfastdoubleClick()){
            return;
        }
        loadNext(RegisterActivity.class);
    }

    private void loginUser(String name , String password){
        String url = String.format(APIURL.LOGIN_URL,name,password, Constants.token);
        RequestUtils.getDataFromUrl(mActivity,url,this,LOGIN_TAG,false,false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public String getFragmentTitle() {
        return null;
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag) {
        mActivity.showToast("登录失败，稍后再试");

    }

    @Override
    public void onGetDataSuccess(String result, String requestTag) {
        if(null != result){
            LoginItem loginItem = GsonUtils.jsonToClass(result,LoginItem.class);
            if(loginItem != null &&  "true".equals(loginItem.result)){
                mActivity.showToast("登录成功");
                MMApplication.mIsLogin = true;
                mActivity.getPreferences().edit().putString(PREF.PREF_USER_ID,loginItem.id).apply();
                Constants.USER_ID = loginItem.id;
                Intent intent = new Intent(Constants.LOGIN_SUCCESS);
                mActivity.sendBroadcast(intent);
                if(tag == Constants.LOGIN){
                    mActivity.finish();
                }else {
                    mActivity.setResult(Constants.UNLOGIN);
                    mActivity.finish();
                }

            }else {
                mActivity.showToast("登录失败，账号或密码有误");
            }
        }
    }
}
