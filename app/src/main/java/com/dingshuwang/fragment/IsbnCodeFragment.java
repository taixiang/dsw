package com.dingshuwang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.dingshuwang.APIURL;
import com.dingshuwang.DataView;
import com.dingshuwang.R;
import com.dingshuwang.adapter.KeywordsAdapter;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.model.SearchItem;
import com.dingshuwang.util.GsonUtils;
import com.dingshuwang.util.RequestUtils;

import butterknife.Bind;

public class IsbnCodeFragment extends BaseFragment implements DataView {

    @Bind(R.id.listview)
    ListView listview;

    @Bind(R.id.tv_none)
    TextView tv_none;

    private String isbn;
    private KeywordsAdapter searchAdapter;

    public static IsbnCodeFragment newInstance(String isbn){
        IsbnCodeFragment fragment = new IsbnCodeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("isbn",isbn);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_isbn,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isbn = (String) getArguments().get("isbn");
        searchAdapter = null;
        doGetIsbn(isbn);
    }

    private void doGetIsbn(String isbn_id){
        String url = String.format(APIURL.CODE_ISBN,isbn_id);
        RequestUtils.getDataFromUrl(mActivity,url,this,"",false,false);
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag) {
        mActivity.showToast("查询失败，稍后再试");
    }

    @Override
    public void onGetDataSuccess(String result, String requestTag) {
        if(null != result){
            SearchItem searchItem = GsonUtils.jsonToClass(result,SearchItem.class);
            if(searchItem != null && "true".equals(searchItem.result)){
                if(null==searchAdapter){
                    searchAdapter = new KeywordsAdapter(searchItem.pros,mActivity);
                    listview.setAdapter(searchAdapter);
                }else {
                    searchAdapter.notifyDataSetChanged();
                }
            }else {
                listview.setVisibility(View.GONE);
                tv_none.setVisibility(View.VISIBLE);
            }

        }

    }

    @Override
    public String getFragmentTitle() {
        return null;
    }
}
