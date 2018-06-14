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
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.model.SearchItem;
import com.dingshuwang.util.GsonUtils;
import com.dingshuwang.util.RequestUtils;
import com.dingshuwang.view.XListView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by tx on 2017/6/9.
 * 关键字搜索
 */

public class KeywordsListFragment extends BaseFragment implements DataView{

    private static final String KEYWORD = "KEYWORD";
    private static final String SEARCH = "SEARCH";

    @Bind(R.id.xListView)
    XListView xListView;

    @Bind(R.id.search)
    ImageView search;
    @Bind(R.id.et_mail)
    EditText et_mail;

    private KeywordsAdapter adapter;
    private int currentPage = 0;
    private int count = 0;
    private LinkedList<SearchItem.Search> mList = new LinkedList<>();
    private String searchKey = "";

    public static KeywordsListFragment newInstance(String keyword){
        KeywordsListFragment fragment = new KeywordsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("keyword",keyword);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_keywordslist,null);
        return view;
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
        searchKey = getArguments().getString("keyword");
        mActivity.setCenterTitle(searchKey);

        getKeywordData(searchKey);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.hideSoftKeyBorard();

                String key =  et_mail.getText().toString();
                mList.clear();
                currentPage = 0;
                count = 0;
                adapter = null;
                if(key.trim().length() <= 0){
                    getKeywordData(searchKey);
                }else {
                    getKeywordData(key);
                }
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

                    xListView.showFooter();
                    getKeywordData(searchKey);
                }
            }
        });


    }

    private void getKeywordData(String keyword ){
        currentPage++;
        String url = String.format(APIURL.SEARCH_URL,keyword,currentPage);
        RequestUtils.getDataFromUrl(mActivity,url,this,KEYWORD,false,false);

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
            if(KEYWORD.equals(requestTag)){
                SearchItem search = GsonUtils.jsonToClass(result,SearchItem.class);
                if(search.result.equals("true") && search.pros != null && search.pros.size() > 0){
                    mList.addAll(search.pros);
                    Log.i("》》》》》  "," mlist size == "+mList.size());
                    if(adapter == null){
                        adapter = new KeywordsAdapter(mList,mActivity);
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
}
