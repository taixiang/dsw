package com.dingshuwang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.dingshuwang.APIURL;
import com.dingshuwang.AdvanceListActivity;
import com.dingshuwang.DataView;
import com.dingshuwang.R;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.model.SearchItem;
import com.dingshuwang.util.GsonUtils;
import com.dingshuwang.util.RequestUtils;
import com.dingshuwang.util.StringUtils;
import com.dingshuwang.util.UIUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by tx on 2017/6/19.
 */

public class SearchAdvancedFragment extends BaseFragment implements DataView{

    private static final String SEARCH_ADVANCE = "search_advance";

    @Bind(R.id.et_name)
    EditText et_name;
    @Bind(R.id.et_author)
    EditText et_author;
    @Bind(R.id.et_isbn)
    EditText et_isbn;
    @Bind(R.id.et_publish)
    EditText et_publish;

    @Bind(R.id.tv_search)
    TextView tv_search;


    public static SearchAdvancedFragment newInstance(){
        return new SearchAdvancedFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_advanced,null);
    }

    @OnClick(R.id.tv_search)
    void search(){
        if(UIUtil.isfastdoubleClick()){
            return;
        }
        String name = et_name.getText().toString();
        String author = et_author.getText().toString();
        String isbn = et_isbn.getText().toString();
        String publish = et_publish.getText().toString();
        if(StringUtils.isEmpty(name) && StringUtils.isEmpty(author) && StringUtils.isEmpty(isbn) && StringUtils.isEmpty(publish)){
            mActivity.showToast("请输入搜索项");
            return;
        }
        getSearchAdv(name,author,isbn,publish);
    }

    private void getSearchAdv(String name,String author,String isbn,String publish){
        String url = String.format(APIURL.SEARCH_ADVANCE,name,author,isbn,publish);
        RequestUtils.getDataFromUrl(mActivity,url,this,SEARCH_ADVANCE,false,false);
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag) {

    }

    @Override
    public void onGetDataSuccess(String result, String requestTag) {
        if(result != null){
            if(SEARCH_ADVANCE.equals(requestTag)){
                SearchItem searchItem = GsonUtils.jsonToClass(result,SearchItem.class);
                if("true".equals(searchItem.result)){
                    AdvanceListActivity.actionAdvance(mActivity,result);
                }else {
                    mActivity.showToast("没有查询到数据");
                }
            }
        }

    }

    @Override
    public String getFragmentTitle() {
        return null;
    }
}
