package com.dingshuwang.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * 检查当前的网络是否可用
 *
 * @author 李波
 * @version 1.0
 */
public class NetWorkUtils
{
    public static final String  IP_REGEX="\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";
    /**
     * 判断当前网络是否可用
     *
     * @param context context
     * @return boolean
     */
    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    /****
     * 是否GPS打开
     *
     * @param context 上下文
     * @return boolean
     */
    public static boolean isGpsEnable(Context context)
    {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /***
     * 当前网络是否是WIFI
     *
     * @param context 上下文
     * @return boolean
     */
    public static boolean isWIFI(Context context)
    {
        boolean isWifi = false;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = manager.getActiveNetworkInfo();
        isWifi = (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI);
        return isWifi;

    }

    /***
     * 当前网络是否是手机网络(3g)
     *
     * @param context 上下文
     * @return boolean
     */
    public static boolean is3G(Context context)
    {
        boolean is3G = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        is3G = (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE);
        return is3G;
    }

    /**
     * 当前网络是否是手机网络(4g)
     *
     * @param context 上下文
     * @return boolean
     */
    public static boolean is4G(Context context)
    {
        boolean is4G = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        is4G = (activeNetInfo != null && activeNetInfo.isConnectedOrConnecting() && activeNetInfo.getType() == TelephonyManager.NETWORK_TYPE_LTE);

        return is4G;
    }

    /***
     * 判断给定的字符串是否是ip字符串
     * @param ipString     ip字符串
     * @return   boolean
     */
    public  static  boolean isIpString(String ipString)
    {
        Pattern pattern = Pattern
                .compile(IP_REGEX);
        Matcher matcher = pattern.matcher(ipString);
        return matcher.matches();
    }
    /**
     * 弹出一个网络不可以使用的对话框
     *
     * @param context context
     * @return alertdialog
     */
    public static AlertDialog showNetworkDisableDialog(Context context)
    {
        AlertDialog dialog;
        dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("网络状况");
        dialog.setIcon(context.getApplicationInfo().icon);
        dialog.setMessage("当前网络不可用,请设置网络");
        dialog.setCanceledOnTouchOutside(false);
        final Context context2 = context;
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "设置", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context2.startActivity(intent);
            }
        });
        return dialog;
    }

}
