package com.dingshuwang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dingshuwang.APIURL;
import com.dingshuwang.Constants;
import com.dingshuwang.DataView;
import com.dingshuwang.R;
import com.dingshuwang.adapter.CouponsAdapter;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.model.CouponsItem;
import com.dingshuwang.util.GsonUtils;
import com.dingshuwang.util.RequestUtils;

import butterknife.Bind;

/**
 * Created by tx on 2017/6/21.
 */

public class CouponsFragment extends BaseFragment implements DataView{

    @Bind(R.id.able_listview)
    ListView ableListView;

    @Bind(R.id.unable_listview)
    ListView unableListView;

    @Bind(R.id.rg)
    RadioGroup radioGroup;
    @Bind(R.id.rb_able)
    RadioButton rb_able;
    @Bind(R.id.rb_unable)
    RadioButton rb_unable;

    //优惠券
    public static final String coupons_url = "coupons_url";

    public static CouponsFragment newInstance(){
        return new CouponsFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        coupons_url();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(rb_able.getId() == checkedId){
                    ableListView.setVisibility(View.VISIBLE);
                    unableListView.setVisibility(View.GONE);
                }else {
                    ableListView.setVisibility(View.GONE);
                    unableListView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_coupons,null);
    }

    //优惠券
    private void coupons_url(){
        String url = String.format(APIURL.coupons_url, Constants.USER_ID);
        RequestUtils.getDataFromUrl(mActivity,url,this,coupons_url,false,false);
    }


    @Override
    public void onGetDataFailured(String msg, String requestTag) {

    }

    @Override
    public void onGetDataSuccess(String result, String requestTag) {
        if(result != null){
            if(coupons_url.equals(requestTag)){
                CouponsItem item = GsonUtils.jsonToClass(result,CouponsItem.class);
                ableListView.setAdapter(new CouponsAdapter(item.coupons,mActivity));
            }
        }
    }

    @Override
    public String getFragmentTitle() {
        return null;
    }
}
