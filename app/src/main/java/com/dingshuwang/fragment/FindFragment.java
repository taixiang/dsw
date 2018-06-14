package com.dingshuwang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dingshuwang.APIURL;
import com.dingshuwang.Constants;
import com.dingshuwang.DataView;
import com.dingshuwang.R;
import com.dingshuwang.SearchFindActivity;
import com.dingshuwang.adapter.FindAdapter;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.model.FindItem;
import com.dingshuwang.util.GsonUtils;
import com.dingshuwang.util.RequestUtils;
import com.dingshuwang.util.UIUtil;
import com.dingshuwang.view.XListView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by tx on 2017/6/6.
 */

public class FindFragment extends BaseFragment implements DataView {

    private static final String FIND_TAG = "find_tag";

    @Bind(R.id.xListView)
    XListView xListView;

    @Bind(R.id.et_mail)
    EditText et_mail;
    @Bind(R.id.search)
    ImageView search;

    private FindAdapter findAdapter;
    private LinkedList<FindItem.Find> mList = new LinkedList<>();
    private int currentPage = 0;
    private boolean isEnd = false;
    private int count = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        xListView.goneHeader();
        xListView.setPullRefreshEnable(false);
        mList.clear();
        findAdapter = null;
        currentPage = 0;
        count = 0;
        getFindData();

        et_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UIUtil.isfastdoubleClick()){
                    return;
                }
                SearchFindActivity.actionSearchFind(mActivity);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UIUtil.isfastdoubleClick()){
                    return;
                }
                SearchFindActivity.actionSearchFind(mActivity);
            }
        });

        xListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
            private int lastItemIndex;// 当前ListView中最后一个Item的索引

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastItemIndex = firstVisibleItem + visibleItemCount;

                if (lastItemIndex == totalItemCount && totalItemCount > 0) {

                    Log.i("》》》》》   last count === ",count+"");
                    if(count > 0){
                        xListView.goneFooter();
                        return;
                    }
                    count++;
//                    if(isEnd){
//                        xListView.goneFooter();
//                        return;
//                    }

                    xListView.showFooter();
                    getFindData();
//                    if (mList.size() % 15 ==0) {
//                        xListView.showFooter();
//                        getFindData();
//                    }else {
//                        xListView.goneFooter();
//                    }

                }
            }
        });

    }

    private void getFindData(){
        currentPage++;
        Log.i("》》》》   ","  currentpage ===  "+currentPage);
        String url = String.format(APIURL.FIND,"",currentPage,Math.random()+"");
        RequestUtils.getDataFromUrl(mActivity,url,this,FIND_TAG,false,false);
    }

    @Override
    public String getFragmentTitle() {
        return null;
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag) {
        currentPage--;
        count = 0;
        onLoad();
    }

    @Override
    public void onGetDataSuccess(String result, String requestTag) {
        if(result != null){
            if(FIND_TAG.equals(requestTag)){
                FindItem findItem = GsonUtils.jsonToClass(result,FindItem.class);
                if(findItem != null && "true".equals(findItem.result) && findItem.pros!= null && findItem.pros.size()>0){
                    mList.addAll(findItem.pros);
                    if(!mList.isEmpty()){
                        if(findAdapter == null){
                            findAdapter = new FindAdapter(mList,mActivity);
                            xListView.setAdapter(findAdapter);
                        }else {
                            findAdapter.notifyDataSetChanged();
                        }
                        count = 0;
                    }

                    Log.i("》》》》 list success == "," "+currentPage );
                }else {
                    Log.i("》》》》 list fail page== "," "+currentPage );
//                    isEnd = true;
                }
            }
        }
        onLoad();
    }

    private void onLoad() {
        xListView.stopLoadMore();
    }

}
