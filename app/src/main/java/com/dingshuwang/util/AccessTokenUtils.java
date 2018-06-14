package com.dingshuwang.util;


import com.dingshuwang.APIURL;
import com.dingshuwang.DataView;
import com.dingshuwang.PREF;
import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.model.APIResult;
import com.dingshuwang.model.AccessTokenItem;


public class AccessTokenUtils
{
    /***
     * 获取Access Code请求的TAG
     */
    public static final String REQUEST_ACCESS_CODE_TAG = "access_code";
    /***
     * 获取Access Token请求的TAG
     */
    public static final String REQUEST_ACCESS_TOKEN_TAG = "access_token";

    /***
     * 获取Access Code
     *
     * @param userName
     * @param pwd
     */
    public static void doGetAccessToken(final BaseActivity activity, CharSequence userName, CharSequence pwd, final OnAccessTokenListener onAccessToken)
    {
//        String accessCodeUrl = APIURL.APIURLUtils.genAccessCodeUrl(userName, pwd);
        String accessCodeUrl ="";
        activity.showProgressDialog();
        DataView dataView = new DataView()
        {
            @Override
            public void onGetDataFailured(String msg, String requestTag)
            {
                onAccessToken.onAccessTokenFailured();
            }

            @Override
            public void onGetDataSuccess(String result, String requestTag)
            {
                APIResult apiResult = GsonUtils.jsonToClass(result, APIResult.class);
                if (null != apiResult )
//                {
//                    if (apiResult.success())
//                    {
//                        if (REQUEST_ACCESS_CODE_TAG.equals(requestTag))
//                        {
//              /* {"status":1,"code":"42e5d5ee-0ab8-3866-a1c1-0acb046772b5","message":"成功获取授权令牌！"}*/
//                            String accessCode = apiResult.code;
////                            String accessTokenUrl = APIURL.APIURLUtils.genAccessTokenUrl(accessCode);
//                            String accessTokenUrl ="";
//                            RequestUtils.getDataFromUrlByPost(activity, accessTokenUrl, "", this, REQUEST_ACCESS_TOKEN_TAG, false);
//
//                        } else if (REQUEST_ACCESS_TOKEN_TAG.equals(requestTag))
//                        {
//                /*{"status":1,"code":"0000009","message":"成功获取访问令牌！","accessToken":{"accessToken":"8fa7a294-ab40-3efb-a292-2f2c980f75c6","refreshToken":"d55a5c0a-7168-371b-b947-dade1b0c147b","expiresIn":1296000,"tokenType":"Bearer","uid":"000c29dfd51b0401506603cdf5000003"}}*/
//                            AccessTokenItem accessTokenItem = GsonUtils.jsonToClass(result, AccessTokenItem.class);
//                            if (null != accessTokenItem && accessTokenItem.success() && accessTokenItem.accessToken != null)
//                            {
//                               activity.getPreferences().edit().putString(PREF.PREF_ACCESS_TOKEN, GsonUtils.toJson(accessTokenItem)).commit();
//                               onAccessToken.onAccessTokenSuccess();
//                            }
//                        }
//                    return;
//                    }
//                }
                onAccessToken.onAccessTokenFailured();
            }
        };
        RequestUtils.getDataFromUrlByPost(activity, accessCodeUrl, "", dataView, REQUEST_ACCESS_CODE_TAG, false);
    }

    public static interface OnAccessTokenListener
    {
        public void onAccessTokenSuccess();

        public void onAccessTokenFailured();

    }
}
