package com.dingshuwang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dingshuwang.DataView;
import com.dingshuwang.R;
import com.dingshuwang.adapter.KeywordsAdapter;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.model.SearchItem;
import com.dingshuwang.util.GsonUtils;

import butterknife.Bind;

/**
 * Created by tx on 2017/6/20.
 */

public class AdvanceListFragment extends BaseFragment implements DataView{

    @Bind(R.id.listView)
    ListView listView;

    public static AdvanceListFragment newInstance(String item){
        AdvanceListFragment fragment = new AdvanceListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("advance",item);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_advancelist,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String advance =  getArguments().getString("advance");
        SearchItem searchItem =  GsonUtils.jsonToClass(advance, SearchItem.class);
        if(null != searchItem && searchItem.pros.size()>0){
            listView.setAdapter(new KeywordsAdapter(searchItem.pros,mActivity));
        }


    }

    @Override
    public void onGetDataFailured(String msg, String requestTag) {

    }

    @Override
    public void onGetDataSuccess(String result, String requestTag) {

    }

    @Override
    public String getFragmentTitle() {
        return null;
    }
}
