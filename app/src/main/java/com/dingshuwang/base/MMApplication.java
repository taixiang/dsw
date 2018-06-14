package com.dingshuwang.base;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;

import com.dingshuwang.Constants;
import com.dingshuwang.PREF;
import com.dingshuwang.R;
import com.dingshuwang.view.AlertDialogUI;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;


public class MMApplication extends Application {
    public static boolean mIsLogin = false;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

        XGPushConfig.enableDebug(this, false);
        XGPushManager.registerPush(mContext);
        Constants.token = XGPushConfig.getToken(mContext);

        Log.i("》》》》  "," token   "+Constants.token);


        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), 0);
        mIsLogin = sharedPreferences.contains(PREF.PREF_USER_ID) ;
        Constants.USER_ID = sharedPreferences.getString(PREF.PREF_USER_ID,"");
    }

    /**
     * 网络是否连接
     *
     * @param
     * @return
     */
    public static boolean checkNetworkInfo() {
        try {
            ConnectivityManager conMan = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo.State mobile = conMan.getNetworkInfo(
                    ConnectivityManager.TYPE_MOBILE).getState();
            NetworkInfo.State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                    .getState();
            if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING)
                return true;
            if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 网络设置
     *
     * @Title: setNetWork
     * @Description:
     * @param @param context 设定文件
     * @return void 返回类型
     * @throws
     */
    static AlertDialogUI alertbBuilder = null;

    public static void setNetWork(final Context context) {
        try {
            if (null == alertbBuilder) {
                alertbBuilder = new AlertDialogUI(context);
            } else {
                alertbBuilder.dismiss();
                alertbBuilder = null;
                alertbBuilder = new AlertDialogUI(context);
            }
            if (null != alertbBuilder) {
                alertbBuilder.setTitle("未打开网络");
                alertbBuilder.setMessage("请打开您的网络连接，稍后再试!");
                alertbBuilder.setPositiveButton("网络设置", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(
                                android.provider.Settings.ACTION_SETTINGS));
                        alertbBuilder.dismiss();
                    }
                });
                alertbBuilder.setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // ((Activity)context).finish();
                        alertbBuilder.dismiss();
                    }
                });
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 网络设置
     *
     * @param @param context 设定文件
     * @return void 返回类型
     * @throws
     * @Title: setNetWork
     * @Description:
     */
    public static void setNetWork(final Context context, final String flag) {
        if (alertbBuilder == null) {
            alertbBuilder = new AlertDialogUI(context);
        } else {
            alertbBuilder.dismiss();
            alertbBuilder = new AlertDialogUI(context);
        }
        alertbBuilder.setTitle("未打开网络");
        alertbBuilder.setMessage("请打开您的网络连接，稍后再试!");
        if (flag.equals("true")) {
            alertbBuilder.setCanceledOnTouchOutside(false);
        }
        alertbBuilder.setPositiveButton("网络设置", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(
                        android.provider.Settings.ACTION_SETTINGS));
                alertbBuilder.dismiss();
            }
        });
        alertbBuilder.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertbBuilder.dismiss();
                if (flag.equals("true")) {
                    System.gc();
                    System.exit(0);
                }
            }
        });
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

}
