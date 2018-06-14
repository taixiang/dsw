package com.dingshuwang.util;

import android.text.TextUtils;

import com.dingshuwang.APIURL;
import com.dingshuwang.DataView;
import com.dingshuwang.PREF;
import com.dingshuwang.R;
import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.model.APIResult;


/**
 * 登录的工具类
 */
public class LoginUtils
{

    public static void doLogin(final BaseActivity activity, CharSequence userName, CharSequence pwd, final OnLoginListener onLoginListener)
    {
        //点击登录时，清除之前的access token 信息，
//        activity.getPreferences().edit().remove(PREF.PREF_ACCESS_TOKEN).commit();
        AccessTokenUtils.OnAccessTokenListener onAccessToken = new AccessTokenUtils.OnAccessTokenListener()
        {
            @Override
            public void onAccessTokenSuccess()
            {
                doGetUserInfo(activity, onLoginListener);
            }

            @Override
            public void onAccessTokenFailured()
            {
                activity.dismissProgressDialog();
                onLoginListener.onLoginFailured(activity.getString(R.string.tv_login_failured_msg));
            }
        };
//        if (!activity.getPreferences().contains(PREF.PREF_ACCESS_TOKEN))
//        {
//            AccessTokenUtils.doGetAccessToken(activity, userName, pwd, onAccessToken);
//        } else
//        {
//            doGetUserInfo(activity, onLoginListener);
//        }
    }

    public static interface OnLoginListener
    {
//        public void onLoginSuccess(UserInfoItem userInfoItem);

        public void onLoginFailured(String failuredMsg);
    }

    public static void doGetUserInfo(final BaseActivity activity, final OnLoginListener onLoginListener)
    {
//        String useInfoUrl = APIURL.GET_USER_INFO;
        String useInfoUrl ="";
        DataView dataView = new DataView()
        {
            @Override
            public void onGetDataFailured(String msg, String requestTag)
            {
                onLoginListener.onLoginFailured(activity.getString(R.string.tv_login_failured_msg));
            }

            @Override
            public void onGetDataSuccess(String result, String requestTag)
            {
                activity.dismissProgressDialog();
                APIResult apiResult = GsonUtils.jsonToClass(result, APIResult.class);
//                if (null != apiResult && apiResult.success())
//                {
//                    UserInfoItem userInfoItem = GsonUtils.jsonToClass(result, UserInfoItem.class);
//                    if (null != userInfoItem)
//                    {
//                        onLoginListener.onLoginSuccess(userInfoItem);
//                        activity.getPreferences().edit().putString(PREF.PREF_USER_INFO, GsonUtils.toJson(userInfoItem)).commit();
//                        updatePrefInfo(activity, userInfoItem);
//                        activity.getMMApplication().mIsLogin = true;
//                        return;
//                    }
//                }
                onLoginListener.onLoginFailured(activity.getString(R.string.tv_login_failured_msg));
            }
        };
        RequestUtils.getDataFromUrl(activity, useInfoUrl, dataView, null, true, true);
    }

    /***
     * 更新之前保存在sharedpreference中的内容
     *
//     * @param userInfoItem
     */
//    private static void updatePrefInfo(BaseActivity activity, UserInfoItem userInfoItem)
//    {
//        if (null != userInfoItem && userInfoItem != null && userInfoItem.customAppMemberVO != null)
//        {
//            String username = userInfoItem.customAppMemberVO.nickname;
//            String birthday = userInfoItem.customAppMemberVO.birthdayStr;
//            String gender = userInfoItem.customAppMemberVO.sex;
//            if (!TextUtils.isEmpty(userInfoItem.customAppMemberVO.province))
//            {
//                String address = userInfoItem.customAppMemberVO.province + " - " + userInfoItem.customAppMemberVO.city;
//                if (userInfoItem.customAppMemberVO.province.equals(userInfoItem.customAppMemberVO.city))
//                {
//                    address = userInfoItem.customAppMemberVO.province;
//                }
//                activity.getPreferences().edit().putString(PREF.PREF_ADDRESS, address).commit();
//            }
//            activity.getPreferences().edit().putString(PREF.PREF_NICKNAME, username).commit();
//            activity.getPreferences().edit().putString(PREF.PREF_GENDER, gender).commit();
//            activity.getPreferences().edit().putString(PREF.PREF_BIRTHDAY, birthday).commit();
//        }
//    }

    public static void doGetUserInfo(final BaseActivity activity, DataView dataView)
    {
//        String useInfoUrl = APIURL.GET_USER_INFO;
//        RequestUtils.getDataFromUrl(activity, useInfoUrl, dataView, null, true, true);
    }

    public static void doGetUserInfo(final BaseActivity activity, DataView dataView, String requestTag)
    {
//        String useInfoUrl = APIURL.getUserInfo;
//        RequestUtils.getDataFromUrl(activity, useInfoUrl, dataView, requestTag, true, false);
    }

}
