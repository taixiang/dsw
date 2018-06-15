package com.dingshuwang;

import android.content.Context;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steven on 2015/10/23 0023.
 */
public class Constants {
    public static String USER_ID;

    public static final String MONEY_SYMBOL = "¥";
    public static final int PAGE_SIZE = 10;
    public static final int PAGE_AC_SIZE = 20;
    public static final String CLIENT_ID = "c1eli113-1leo-4fly-ab30-77c1024b";       // App_key
    public static String CLIENT_SECRET = "d912b203-z1130-43cc-6bc8-9702cd201";  //App_Secret
    public static final String REDIRECT_URI = "native:oauth2:ab:0072";
    public static float coupon = 0;
    public static String token ;
    public static List<String> qrCodeList = new ArrayList<>();

    /***
     * 商家Id
     */
    public static final String STORE_ID = "511802";//"559144";//"505236";// "505236";//"505236"; 501766;556040;503815;509011(阿里),560894

    public static final String STORE_PAY_EVN = "prd";//"prd"、dev

    // appid 微信支付
    // 请同时修改 androidmanifest.xml里面，.PayActivityd里的属性<data
    // android:scheme="wx8fa68b04cf0fce85"/>为新设置的appid
    public static final String APP_ID = "wxe20b563509380693";

    // 商户号
    public static final String MCH_ID = "1330115101";//1293942601

    // API密钥，在商户平台设置
    public static final String API_KEY = "7d2kxfg83l1wro2pn6eg9mjzrmyfhpwx";

    /**
     * 支付宝Key
     **/
    public static final String PARTNER = "2088811528631908";
    public static final String SELLER = "2260274497@qq.com";
    public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALSQe/gSbGDVZqoF\n" +
            "byaLwryDo1EzMlHknG8Ols5L2sKwL99GO8mAi1m4gOEA72nGpQ1mit8Y7ICTZGQ7\n" +
            "2gwSJX3i2mhyyF22z3WAL32vIdYJGIyNlc3X2nwkxrX/FpAuXkGq7sCdnFNYGZXx\n" +
            "MJpH6cAB3i/AfdXOudjgrRYEJ0YVAgMBAAECgYEAjtwx+Vg6P3MYQzUBeDHj5VsR\n" +
            "gFFNYtXJn2SflKEXeCoF9lWPQCJgHqCH933R7pKoTC3xegoyNJhpOZTRM/O3jMlk\n" +
            "oGILowh9/A2aUvvFEQx8Pz6Gt6AwdBP3McKxF105m++UzT7utpEYhZEjp3kURq0U\n" +
            "XnzfSPQA3TbHwByGYRECQQDlHxJTc2+288wqpOm3eZkqxsj1pdnDa8KWttDsVsJe\n" +
            "MrEzAdONsEl2WlFV34qZT0QIFdXEC49hzjL8yVE6sVB7AkEAyb8oqvkxSDEcT+Gq\n" +
            "godLD1k69adyLfJ8UA4rDXtnmmjv3h7TkXPmpsNIlnPV2tJVk/G6DnfQv2Gen97+\n" +
            "uk0mrwJBAMNiZs9RPnAGoRGwhjnW8R3AXLjWUMhMSakahzkzlxabJe74XL6UGWFb\n" +
            "DccsFNY9+Sbn7935ebEPFP3qc2GYuacCQQCy2Y+A+scQu9DglCsn6i7FnZIiQt57\n" +
            "EzPXeKf8HMF85rh3Dpb6pGf+wLKyBmC4y6xoKmyJ9PgPIciQ7AygNlMrAkBoVuCC\n" +
            "Tc9TLtvR9SH6KRWPGai0SBPuxgf2lZXGvtTXtstVWrrK/acAEMBXKd1L+G9ytiom\n" +
            "NAnQhHAZGMVPyHKl";
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
    public static final int SDK_PAY_FLAG = 1;
    public static final int SDK_CHECK_FLAG = 2;

    public static final int INTENT_PAYMONEY_FORRESULT_CODE = 1200;

    public static final String ENVIROMENT_DIR_CACHE = "/sdcard/custom361app/cache/";
    public static final String COUSUM_FLAG = "com.maimeng.mine.receive";
    public static final String COUSUM_FLAG_WXPAY = "com.maimeng.mine.receive.wxpay";
    public static final String COUSUM_FLAG_OK = "com.maimeng.mine.receive.pay.ok";
    public static final String COUSUM_FLAG_CLEAR = "com.maimeng.mine.receive.pay.clear";
    public static final String LOGIN_FLAG_CLEAR = "com.dingshuw.login.finish";
    public static final String LOGIN_SUCCESS = "com.dingshuw.login.success";
    public static final String DELETE_GOOD = "com.dingshuw.delete";
    public static final String GOOD_NUM = "com.dingshuw.good_num";
    public static final String DELETE_ADDRESS = "com.dingshuw.delete.address";
    public static final String DEFAULT_ADDRESS = "com.dingshuw.default.address";
    public static final String EDIT_ADDRESS = "com.dingshuw.edit.address";
    public static final String PHOTO_URL = "com.dingshuwang.photo";
    public static final String PHOTO_URL_2 = "com.dingshuwang.photo_2";
    public static final String NAME_MODIFY = "com.dingshuwang.modifyname";
    public static final String GOTOFIRST = "com.dingshuwang.gofirst";


    public static final String TOSHOPCART = "toshopcart"; //购物车 101

    //

    public static final int UNLOGIN = 100; //未登陆返回首页 100

    public static final int LOGIN = 200; //登陆返回当前页面

    public static final int CAMMER = 300; //首页扫描isbn返回 resultcode

    public static final int CAMMER_PUBLISH = 600; //发布扫描isbn返回 resultcode

    public static final int CODE_PUBLISH = 400; //发布扫描返回 request

    public static final int CODE_HOME=500;//首页扫描返回  requestcode

    public static final int CAMMER_PURCHASE = 700; //采购扫描isbn返回 resultcode

    public static final int CODE_ISBN = 800; //专业扫描isbn返回 requestcode
    public static final int CODE_ISBN_RES = 900; //专业扫描isbn返回 resultcode


    /**
     * 图片浏览
     *
     */
    static public class PhotoView {
        public static boolean clickPhoto = false;
        public static Context pvContext;
        public static ImageView imageView;
    }

}
