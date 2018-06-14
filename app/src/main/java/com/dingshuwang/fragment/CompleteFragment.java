package com.dingshuwang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dingshuwang.APIURL;
import com.dingshuwang.Constants;
import com.dingshuwang.DataView;
import com.dingshuwang.R;
import com.dingshuwang.adapter.TopayAdapter;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.model.WaitPayItem;
import com.dingshuwang.util.GsonUtils;
import com.dingshuwang.util.RequestUtils;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/7/29.
 */
public class CompleteFragment extends BaseFragment implements DataView {

    @Bind(R.id.listview)
    ListView listView;

    public static CompleteFragment newInstance(){
        return new CompleteFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_to_pay,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        doData();
    }

    private void doData(){
        String url = String.format(APIURL.complete_url, Constants.USER_ID);
        RequestUtils.getDataFromUrl(mActivity,url,this,"",false,false);
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
        if(null != result){
            WaitPayItem item = GsonUtils.jsonToClass(result,WaitPayItem.class);
            if(null != item && null != item.shops){
                listView.setAdapter(new TopayAdapter(item.shops,mActivity,4));
            }
        }
    }
}
