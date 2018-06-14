package com.dingshuwang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.dingshuwang.APIURL;
import com.dingshuwang.Constants;
import com.dingshuwang.DataView;
import com.dingshuwang.R;
import com.dingshuwang.SearchAdvancedActivity;
import com.dingshuwang.SearchCommonActivity;
import com.dingshuwang.TestAct;
import com.dingshuwang.WebViewAct;
import com.dingshuwang.adapter.ColumnAdapter;
import com.dingshuwang.adapter.FreeAdapter;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.model.HomeFreeItem;
import com.dingshuwang.model.HomeMiddleItem;
import com.dingshuwang.model.TestItem;
import com.dingshuwang.util.GlideImgManager;
import com.dingshuwang.util.GsonUtils;
import com.dingshuwang.util.RequestUtils;
import com.dingshuwang.util.UIUtil;
import com.dingshuwang.view.CustomGridView;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by tx on 2017/6/6.
 */

public class HomeFragment extends BaseFragment implements DataView{


    private static final String HOME_MIDDLE = "home_middle";
    private static final String HOME_SHARE = "home_share";
    private static final String HOME_FREE = "home_free";
    private static final String HOME_BOTTOM = "home_bottom";

    @Bind(R.id.grid_column)
    CustomGridView grid_column;

    @Bind(R.id.grid_free)
    CustomGridView grid_free;

    @Bind(R.id.iv_share)
    ImageView iv_share;

    @Bind(R.id.iv_bottom)
    ImageView iv_bottom;

    @Bind(R.id.et_mail)
    EditText et_mail;
    @Bind(R.id.iv_search)
    ImageView iv_search;

    private ColumnAdapter columnAdapter;
    private FreeAdapter freeAdapter;
    private HomeMiddleItem homeMiddleItem;
    private HomeMiddleItem homeBottomItem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        columnAdapter = null;
        freeAdapter = null;
        getMiddle();
        getShare();
        getFree();
        getBottom();
        et_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UIUtil.isfastdoubleClick()){
                    return;
                }
                SearchCommonActivity.actionSearchCommon(mActivity);
            }
        });
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UIUtil.isfastdoubleClick()){
                    return;
                }
                SearchCommonActivity.actionSearchCommon(mActivity);
            }
        });

    }

    private void getMiddle2(){
        String url = "http://www.mocky.io/v2/598ab678110000c700515cc0";
        RequestUtils.getDataFromUrl(mActivity,url,this,"test",false,false);
    }

    //中间小说区域
    private void getMiddle(){
        String url = APIURL.Home_Middle;
        RequestUtils.getDataFromUrl(mActivity,url,this,HOME_MIDDLE,false,false);
    }

    //享图书
    private void getShare(){
        String url = APIURL.HOME_SHARE;
        RequestUtils.getDataFromUrl(mActivity,url,this,HOME_SHARE,false,false);
    }

    //免费领取
    private void getFree(){
        String url = APIURL.HOME_FREE;
        RequestUtils.getDataFromUrl(mActivity,url,this,HOME_FREE,false,false);
    }

    //底部图片
    private void getBottom(){
        String url = APIURL.HOME_BOTTOM;
        RequestUtils.getDataFromUrl(mActivity,url,this,HOME_BOTTOM,false,false);
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

    @OnClick(R.id.highs)
    void searchAdvanced(){
        if(UIUtil.isfastdoubleClick()){
            return;
        }
        SearchAdvancedActivity.actionSearchAdvaced(mActivity);
    }

    @OnClick(R.id.iv_share)
    void share(){
        if(UIUtil.isfastdoubleClick()){
            return;
        }
        String url = homeMiddleItem.data.get(0).url;
        String title = homeMiddleItem.data.get(0).keywords;
        if(homeMiddleItem != null &&null!= url&& title != null){
            WebViewAct.actionWebView(mActivity,title,url);
        }
    }

    @OnClick(R.id.iv_bottom)
    void bottom(){
        if(UIUtil.isfastdoubleClick()){
            return;
        }
        String url = homeBottomItem.data.get(0).url;
        String title = homeBottomItem.data.get(0).keywords;
        if(homeBottomItem != null &&null!= url&& title != null){
            WebViewAct.actionWebView(mActivity,"",url);
        }

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
        if(result != null){

            if(HOME_MIDDLE.equals(requestTag)){
                HomeMiddleItem homeMiddleItem = GsonUtils.jsonToClass(result,HomeMiddleItem.class);
                if(homeMiddleItem.result && homeMiddleItem.data.size() > 0){
                    if(columnAdapter == null){
                        columnAdapter = new ColumnAdapter(homeMiddleItem.data,mActivity);
                        grid_column.setAdapter(columnAdapter);
                    }else {
                        columnAdapter.notifyDataSetChanged();
                    }
                }
            }else if(HOME_SHARE.equals(requestTag)){
                 homeMiddleItem = GsonUtils.jsonToClass(result,HomeMiddleItem.class);
                if(homeMiddleItem.result && homeMiddleItem.data.size() > 0){
                    GlideImgManager.loadImage(mActivity,homeMiddleItem.data.get(0).image_url,iv_share);
                }
            }else if(HOME_FREE.equals(requestTag)){
                HomeFreeItem freeItem = GsonUtils.jsonToClass(result,HomeFreeItem.class);
                if(freeItem.result && freeItem.data.size()>0){
                    if(null == freeAdapter){
                        freeAdapter = new FreeAdapter(freeItem.data,mActivity);
                        grid_free.setAdapter(freeAdapter);
                    }else {
                        freeAdapter.notifyDataSetChanged();
                    }
                }
            }else if(HOME_BOTTOM.equals(requestTag)){
                 homeBottomItem = GsonUtils.jsonToClass(result,HomeMiddleItem.class);
                if(homeBottomItem.result && homeBottomItem.data.size() > 0){
                    GlideImgManager.loadImage(mActivity,homeBottomItem.data.get(0).image_url,iv_bottom);
                }
            }else {
                Log.i("  testItem  result ",result );

                    TestItem item = GsonUtils.jsonToClass(result,TestItem.class);
                if(item != null){
                    Log.i("  testItem  ",item.toString());
                }




            }

        }
    }
}
