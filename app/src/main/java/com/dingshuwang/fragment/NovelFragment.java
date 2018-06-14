package com.dingshuwang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;

import com.dingshuwang.APIURL;
import com.dingshuwang.DataView;
import com.dingshuwang.R;
import com.dingshuwang.adapter.KeywordsAdapter;
import com.dingshuwang.adapter.NovelAdapter;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.model.NovelItem;
import com.dingshuwang.model.SearchItem;
import com.dingshuwang.util.GsonUtils;
import com.dingshuwang.util.RequestUtils;
import com.dingshuwang.view.XListView;

import java.util.LinkedList;

import butterknife.Bind;

/**
 * Created by tx on 2017/6/15.
 * 小说专区
 */

public class NovelFragment extends BaseFragment implements DataView{

    private static final String KEYWORD = "KEYWORD";
    private static final String SEARCH = "SEARCH";

    @Bind(R.id.xListView)
    XListView xListView;

    @Bind(R.id.search)
    ImageView search;
    @Bind(R.id.et_mail)
    EditText et_mail;

    private NovelAdapter adapter;
    private int currentPage = 0;
    private int count = 0;
    private LinkedList<NovelItem.Novel> mList = new LinkedList<>();

    public static NovelFragment newInstance(){
        return new NovelFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_novel,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        xListView.goneHeader();
        xListView.setPullRefreshEnable(false);
        mList.clear();
        adapter = null;
        currentPage = 0;
        count = 0;

        getNovelData();

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

                    xListView.showFooter();
                    getNovelData();
                }
            }
        });

    }

    private void getNovelData(){
        currentPage++;
        String url = String.format(APIURL.NOVEL_URL,currentPage);
        RequestUtils.getDataFromUrl(mActivity,url,this,KEYWORD,false,false);
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
            if(KEYWORD.equals(requestTag)){
                NovelItem novel = GsonUtils.jsonToClass(result,NovelItem.class);
                if(novel.result&& novel.pros != null && novel.pros.size() > 0){
                    mList.addAll(novel.pros);
                    Log.i("》》》》》  "," mlist size == "+mList.size());
                    if(adapter == null){
                        adapter = new NovelAdapter(mList,mActivity);
                        xListView.setAdapter(adapter);
                    }else {
                        adapter.notifyDataSetChanged();
                    }
                    count = 0;
                }
            }
        }
        onLoad();

    }

    private void onLoad() {
        xListView.stopLoadMore();
    }


    @Override
    public String getFragmentTitle() {
        return null;
    }
}
