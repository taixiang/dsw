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
import com.dingshuwang.adapter.CollectListAdapter;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.model.CollectListItem;
import com.dingshuwang.util.GsonUtils;
import com.dingshuwang.util.RequestUtils;

import butterknife.Bind;

/**
 * Created by tx on 2017/6/24.
 */

public class CollectListFragment extends BaseFragment implements DataView{

    @Bind(R.id.listview)
    ListView listView;

    private static final String COLLECT_LIST = "collect_list_url";

    public static CollectListFragment newInstance(){
        return new CollectListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_collect_list,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        collectList();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void collectList(){
        String url = String.format(APIURL.COLLECT_LIST_URL, Constants.USER_ID);
        RequestUtils.getDataFromUrl(mActivity,url,this,COLLECT_LIST,false,false);

    }

    @Override
    public void onGetDataFailured(String msg, String requestTag) {

    }

    @Override
    public void onGetDataSuccess(String result, String requestTag) {
        if(result!=null){
            if(COLLECT_LIST.equals(requestTag)){
                CollectListItem item = GsonUtils.jsonToClass(result,CollectListItem.class);
                if(item != null && item.result && item.favorites != null){
                    listView.setAdapter(new CollectListAdapter(mActivity,item.favorites));
                }
            }
        }
    }

    @Override
    public String getFragmentTitle() {
        return null;
    }
}
