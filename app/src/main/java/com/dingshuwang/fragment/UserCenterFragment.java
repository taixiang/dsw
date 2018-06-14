package com.dingshuwang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.dingshuwang.APIURL;
import com.dingshuwang.CompleteActivity;
import com.dingshuwang.Constants;
import com.dingshuwang.DataView;
import com.dingshuwang.ExpOrderActivity;
import com.dingshuwang.ForExpActivity;
import com.dingshuwang.PREF;
import com.dingshuwang.R;
import com.dingshuwang.ToPayActivity;
import com.dingshuwang.adapter.UserAdapter;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.MMApplication;
import com.dingshuwang.model.CouponsItem;
import com.dingshuwang.model.PointItem;
import com.dingshuwang.model.SignItem;
import com.dingshuwang.model.UserItem;
import com.dingshuwang.util.GsonUtils;
import com.dingshuwang.util.RequestUtils;
import com.dingshuwang.util.StringUtils;
import com.dingshuwang.util.UIUtil;
import com.dingshuwang.view.AlertDialogUI;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by tx on 2017/6/6.
 */

public class UserCenterFragment extends BaseFragment implements DataView{

    /**
     * 用户信息
     */
    public static final String USER_INFO = "USER_INFO";
    /**
     * 签到
     */
    public static final String QIANDAO = "qiandao";

    //优惠券
    public static final String coupons_url = "coupons_url";

    //积分
    public static final String point_url = "point_url";

    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.tv_id)
    TextView tv_id;
    @Bind(R.id.tv_qiandao)
    TextView tv_qiandao;

    @Bind(R.id.gridView)
    GridView gridView;
    private int[] iv_ids = {R.mipmap.menu1,R.mipmap.menu3,R.mipmap.menu4,R.mipmap.menu5,R.mipmap.menu6,R.mipmap.menu2,R.mipmap.menu7,R.mipmap.menu8,R.mipmap.dfh,};
    private String[] names = {"我的订单","优惠券","我的积分","购物车","我的收藏","我的发布","收货地址","图书回收","联系我们"};
    private UserAdapter userAdapter ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("》》》》》  "," usercenter create ");
        userAdapter = new UserAdapter(iv_ids,names,mActivity);
        gridView.setAdapter(userAdapter);



    }

    @Override
    public void onResume() {
        super.onResume();
        doGetMsg();

        qiandao();
        coupons_url();
        point_url();
    }

    @OnClick(R.id.tv_qiandao)
    void sign_in(){
        if(UIUtil.isfastdoubleClick()){
            return;
        }
        qiandao();
    }

    //待付款
    @OnClick(R.id.ll_dfk)
    void topay(){
        if(UIUtil.isfastdoubleClick()){
            return;
        }
        ToPayActivity.actToPay(mActivity);
    }

    //待发货
    @OnClick(R.id.ll_dfh)
    void waitExp(){
        if(UIUtil.isfastdoubleClick()){
            return;
        }
        ExpOrderActivity.actExpOrder(mActivity);
    }

    //待收货
    @OnClick(R.id.ll_dsh)
    void for_exp(){
        if(UIUtil.isfastdoubleClick()){
            return;
        }
        ForExpActivity.actForExp(mActivity);
    }

    //已完成
    @OnClick(R.id.ll_dpj)
    void complete(){
        if(UIUtil.isfastdoubleClick()){
            return;
        }
        CompleteActivity.actComplete(mActivity);
    }

    @OnClick(R.id.btn_out)
    void btnOut(){
        final AlertDialogUI dialogUI = new AlertDialogUI(mActivity);
        dialogUI.setMessage("是否确定退出");
        dialogUI.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialogUI != null){
                    dialogUI.dismiss();
                }
            }
        });
        dialogUI.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.getPreferences().edit().remove(PREF.PREF_USER_ID).apply();
                Constants.USER_ID = "";
                MMApplication.mIsLogin = false;
                mActivity.finish();
            }
        });
    }

    //个人信息
    private void doGetMsg(){
        String url = String.format(APIURL.USER_INFO_URL, Constants.USER_ID);
        RequestUtils.getDataFromUrlByPost(mActivity,url,"",this,USER_INFO,false,false);
    }

    //签到
    private void qiandao(){
        String url = String.format(APIURL.sign_in_url,Constants.USER_ID);
        RequestUtils.getDataFromUrl(mActivity,url,this,QIANDAO,false,false);
    }

    //优惠券
    private void coupons_url(){
        String url = String.format(APIURL.coupons_url,Constants.USER_ID);
        RequestUtils.getDataFromUrl(mActivity,url,this,coupons_url,false,false);
    }

    //积分
    private void point_url(){
        String url = String.format(APIURL.point,Constants.USER_ID);
        RequestUtils.getDataFromUrl(mActivity,url,this,point_url,false,false);
    }

    @Override
    public String getFragmentTitle() {
        return null;
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag) {

    }

    @Override
    public void onGetDataSuccess(String result, String requestTag) {
        if(result != null){
            if(USER_INFO.equals(requestTag)){
                UserItem item = GsonUtils.jsonToClass(result,UserItem.class);
                if(item != null){
                    tv_name.setText(item.user_name);
                    tv_id.setText("ID："+item.id);
                }
            }else if(QIANDAO.equals(requestTag)){
                SignItem item = GsonUtils.jsonToClass(result,SignItem.class);
                if(item!=null){
                    if(item.result){
                        tv_qiandao.setText("签到");
                    }else {
                        tv_qiandao.setText("已签到");
                    }
                }
            }else if(coupons_url.equals(requestTag)){
                CouponsItem item = GsonUtils.jsonToClass(result,CouponsItem.class);
                if(item != null && item.coupons != null && item.coupons.size()>0){
                    names[1]="优惠券("+item.coupons.size()+")";
                    userAdapter.notifyDataSetChanged();
                }
            }else if(point_url.equals(requestTag)){
                PointItem item = GsonUtils.jsonToClass(result,PointItem.class);
                if(item != null && item.message != null && item.message.size()>0){
                    double total = 0;
                    for(PointItem.Point point : item.message){
                        total+=point.value;
                    }
                    String d = StringUtils.doubleFormat(total);
                    names[2]="我的积分("+d+")";
                    userAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
