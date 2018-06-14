package com.dingshuwang.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;

import com.dingshuwang.APIURL;
import com.dingshuwang.Constants;
import com.dingshuwang.DataView;
import com.dingshuwang.IsbnCodeActivity;
import com.dingshuwang.R;
import com.dingshuwang.adapter.FindAdapter;
import com.dingshuwang.adapter.KeywordsAdapter;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.model.FindItem;
import com.dingshuwang.model.SearchItem;
import com.dingshuwang.util.GsonUtils;
import com.dingshuwang.util.RequestUtils;
import com.dingshuwang.util.UIUtil;
import com.dingshuwang.view.XListView;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.util.LinkedList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by tx on 2017/6/14.
 */

public class SearchFindFragment extends BaseFragment implements DataView {

    private static final String SEARCH_KEY = "search_key";

    @Bind(R.id.xListView)
    XListView xListView;

    @Bind(R.id.search)
    ImageView search;
    @Bind(R.id.et_mail)
    EditText et_mail;
    @Bind(R.id.iv_back)
    ImageView iv_back;

    private FindAdapter adapter;
    private int currentPage = 0;
    private int count = 0;
    private LinkedList<FindItem.Find> mList = new LinkedList<>();
    private String key = "";

    public static SearchFindFragment newInstance(){
        return new SearchFindFragment();
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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(et_mail,0);
            }
        },800);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.hideSoftKeyBorard();

                key =  et_mail.getText().toString();
                mList.clear();
                currentPage = 0;
                count = 0;
                adapter = null;
                if(key.trim().length() <= 0){
                    mActivity.showToast("请输入关键字");
                }else {
                    getSearchData(key);
                }
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
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

                    if(count > 0){
                        xListView.goneFooter();
                        return;
                    }
                    count++;

                    xListView.showFooter();
                    getSearchData(key);
                }
            }
        });

    }

    private void getSearchData(String keyword){
        currentPage++;
        String url = String.format(APIURL.FIND,keyword,currentPage,Math.random()+"");
        RequestUtils.getDataFromUrl(mActivity,url,this,SEARCH_KEY,false,false);

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_searchfind,null);
    }

    @OnClick(R.id.iv_sao)
    void cammer(){
        if(UIUtil.isfastdoubleClick()){
            return;
        }
        Intent openCameraIntent = new Intent(mActivity, CaptureActivity.class);
        openCameraIntent.putExtra("tag_code",1);
        mActivity.startActivityForResult(openCameraIntent, Constants.CODE_HOME);
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag) {

    }

    @Override
    public void onGetDataSuccess(String result, String requestTag) {
        if(result != null){
            if(SEARCH_KEY.equals(requestTag)){
                FindItem findItem = GsonUtils.jsonToClass(result,FindItem.class);
                if(findItem != null && findItem.result != null && findItem.result.equals("true") && findItem.pros != null && findItem.pros.size() > 0){
                    mList.addAll(findItem.pros);
                    Log.i("》》》》》  "," mlist size == "+mList.size());
                    if(adapter == null){
                        adapter = new FindAdapter(mList,mActivity);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("》》》》》 search find","requestCode ==== "+requestCode+"");
        Log.i("》》》》》 search find","resultcode ==== "+resultCode+"");

        if(requestCode == Constants.CODE_HOME){ //首页扫描后跳列表页
            if(resultCode == Constants.CAMMER){
                Bundle bundle = data.getExtras();
                String scanResult = bundle.getString("result");
                Log.i("》》》》》  "," scanresult === "+scanResult);
                if(scanResult != null){
                    IsbnCodeActivity.actIsbn(mActivity,scanResult);
                }
            }
        }
    }

}
