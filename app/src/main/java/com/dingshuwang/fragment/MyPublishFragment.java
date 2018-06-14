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
import com.dingshuwang.adapter.MyPublishAdapter;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.model.PublishListItem;
import com.dingshuwang.util.GsonUtils;
import com.dingshuwang.util.RequestUtils;

import butterknife.Bind;

/**
 * Created by tx on 2017/7/7.
 */

public class MyPublishFragment extends BaseFragment implements DataView{

    private static final String MYPUBLISH = "my_publish";


    @Bind(R.id.listview)
    ListView listView;

    public static MyPublishFragment newInstance(){
        return new MyPublishFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_publish,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getPublish();
    }

    private void getPublish(){
        String url = String.format(APIURL.MY_PUBLISH, Constants.USER_ID);
        RequestUtils.getDataFromUrl(mActivity,url,this,MYPUBLISH,false,false);
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag) {

    }

    @Override
    public void onGetDataSuccess(String result, String requestTag) {
        if(result != null){
            PublishListItem item = GsonUtils.jsonToClass(result,PublishListItem.class);
            if(item != null && item.result){
                listView.setAdapter(new MyPublishAdapter(mActivity,item.trade_infor));
            }
        }

    }

    @Override
    public String getFragmentTitle() {
        return null;
    }
}
