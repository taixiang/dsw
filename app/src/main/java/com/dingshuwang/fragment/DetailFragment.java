package com.dingshuwang.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dingshuwang.APIURL;
import com.dingshuwang.Constants;
import com.dingshuwang.DataView;
import com.dingshuwang.LoginActivity;
import com.dingshuwang.MainActivity;
import com.dingshuwang.R;
import com.dingshuwang.ShopCartActivity;
import com.dingshuwang.adapter.CommentAdapter;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.MMApplication;
import com.dingshuwang.model.AddCartItem;
import com.dingshuwang.model.CollectItem;
import com.dingshuwang.model.CommentItem;
import com.dingshuwang.model.DetailItem;
import com.dingshuwang.model.GroupInfoItem;
import com.dingshuwang.util.GlideImgManager;
import com.dingshuwang.util.GsonUtils;
import com.dingshuwang.util.RequestUtils;
import com.dingshuwang.util.UIUtil;
import com.dingshuwang.view.CustomListView;
import com.dingshuwang.view.XListView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by tx on 2017/6/14.
 */

public class DetailFragment extends BaseFragment implements DataView,View.OnClickListener{

    private static final String DETAIL_TAG = "detail_tag";
    private static final String GROUP_INFO_TAG = "group_info_tag";
    private static final String COMMENT_TAG = "comment_tag";
    private static final String ADD_CART_TAG = "ADD_CART_TAG";
    private static final String COLLECT_TAG = "COLLECT_TAG";
    private static final String COLLECT_CANCEL_TAG = "COLLECT_CANCEL_TAG";
    private static final String SHOW_COLLECT = "show_collect";


    @Bind(R.id.iv_logo)
    ImageView iv_logo;
    @Bind(R.id.tv_name)
    TextView tv_name;

    @Bind(R.id.iv_minus)
    ImageView iv_minus;
    @Bind(R.id.tv_count)
    TextView tv_count;
    @Bind(R.id.iv_add)
    ImageView iv_add;

    @Bind(R.id.tv_isbn)
    TextView tv_isbn;
    @Bind(R.id.tv_price_sell)
    TextView tv_price_sell;
    @Bind(R.id.goods_nums) //库存
    TextView goods_nums;
    @Bind(R.id.tv_sale_nums)
    TextView tv_sale_nums;

    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.listview)
    CustomListView listView;

    @Bind(R.id.tv_info)
    TextView tv_info;

    @Bind(R.id.rg)
    RadioGroup radioGroup;
    @Bind(R.id.rb_detail)
    RadioButton rb_detail;
    @Bind(R.id.rb_comment)
    RadioButton rb_comment;

    @Bind(R.id.com_container)
    LinearLayout com_container;

    @Bind(R.id.iv_collect)
    ImageView iv_collect;
    @Bind(R.id.tv_collect)
    TextView tv_collect;
    private int count = 0;
    private int sell_count;
    private String id;

    private boolean isCollect; //是否收藏

    public static DetailFragment newInstance(String id){
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        id = getArguments().getString("id");
        getDetailData(id);
        showCollect();
        iv_minus.setOnClickListener(this);
        iv_add.setOnClickListener(this);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(rb_detail.getId() == checkedId){
                    webView.setVisibility(View.VISIBLE);
                    com_container.setVisibility(View.GONE);
                }else {
                    webView.setVisibility(View.GONE);
                    com_container.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    //详情
    private void getDetailData(String id){
        String url =String.format(APIURL.DETAIL_URL,id);
        RequestUtils.getDataFromUrl(mActivity,url,this,DETAIL_TAG,false,false);
    }

    //团购批发价
    private void getGroupInfoData(){
        String url = APIURL.DETAIL_GROUPINFO;
        RequestUtils.getDataFromUrl(mActivity,url,this,GROUP_INFO_TAG,false,false);
    }

    //评价
    private void getComment(){
        String url = String.format(APIURL.Comment_url,id);
        RequestUtils.getDataFromUrl(mActivity,url,this,COMMENT_TAG,false,false);
    }

    //加入购物车
    private void doAddCart(){
        String url = String.format(APIURL.CART_ADD_URL, Constants.USER_ID,id,tv_count.getText().toString());
        RequestUtils.getDataFromUrlByPost(mActivity,url,"",this,ADD_CART_TAG,false,false);
    }

    //收藏
    private void collectGoods(){
        String url = String.format(APIURL.COLLECT_URL,Constants.USER_ID,id);
        RequestUtils.getDataFromUrlByPost(mActivity,url,"",this,COLLECT_TAG,false,false);
    }
    //展示收藏
    private void showCollect(){
        String url = String.format(APIURL.COLLECT_URL,Constants.USER_ID,id);
        RequestUtils.getDataFromUrlByPost(mActivity,url,"",this,SHOW_COLLECT,false,false);
    }

    //取消收藏
    private void collectCancelGoods(){
        String url = String.format(APIURL.COLLECT_CANCEL_URL,Constants.USER_ID,id);
        RequestUtils.getDataFromUrlByPost(mActivity,url,"",this,COLLECT_CANCEL_TAG,false,false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail,null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    //加入购物车
    @OnClick(R.id.add_cart)
    void addCart(){
        if(UIUtil.isfastdoubleClick()){
            return;
        }
        if(MMApplication.mIsLogin){
            doAddCart();
        }else {
            LoginActivity.actionLogin(mActivity,Constants.LOGIN);
        }
    }

    //收藏
    @OnClick(R.id.collect)
    void collect(){
        if(UIUtil.isfastdoubleClick()){
            return;
        }
        if(MMApplication.mIsLogin){
            if(!isCollect){
                collectGoods();
            }else {
                collectCancelGoods();
            }
        }else {
            LoginActivity.actionLogin(mActivity,Constants.LOGIN);
        }
    }

    //购物车界面
    @OnClick(R.id.shopcart)
    void shopcart(){
        if(UIUtil.isfastdoubleClick()){
            return;
        }
        if(MMApplication.mIsLogin){
            ShopCartActivity.actShop(mActivity);
        }else {
            LoginActivity.actionLogin(mActivity,Constants.LOGIN);
        }

    }



    @Override
    public void onGetDataFailured(String msg, String requestTag) {

    }

    @Override
    public void onGetDataSuccess(String result, String requestTag) {
        if(result != null){
            if(DETAIL_TAG.equals(requestTag)){
                DetailItem detailItem = GsonUtils.jsonToClass(result,DetailItem.class);
                if(detailItem != null && detailItem.result){
                    getGroupInfoData();
                    getComment();
                    GlideImgManager.loadImage(mActivity,detailItem.img_url,iv_logo);
                    tv_name.setText(detailItem.name);
                    sell_count = Integer.parseInt(detailItem.goods_nums);
                    if(Integer.parseInt(detailItem.goods_nums) == 0){
                        iv_minus.setEnabled(false);
                        iv_add.setEnabled(false);
                        tv_count.setText("0");
                    }

                    tv_isbn.setText(detailItem.ISBN);
                    tv_price_sell.setText("￥"+detailItem.price_sell);
                    goods_nums.setText(detailItem.goods_nums);
                    tv_sale_nums.setText(detailItem.sale_nums);

                    String htmlData = detailItem.description;
                    if(null != htmlData){
                        htmlData = htmlData.replaceAll("&", "");
                        htmlData = htmlData.replaceAll("quot;", "\"");
                        htmlData = htmlData.replaceAll("lt;", "<");
                        htmlData = htmlData.replaceAll("gt;", ">");
                        htmlData = htmlData.replaceAll("nbsp", " ");
                        webView.loadDataWithBaseURL(null, htmlData, "text/html", "utf-8", null);
                    }
                }
            }else if(GROUP_INFO_TAG.equals(requestTag)){
                GroupInfoItem item = GsonUtils.jsonToClass(result,GroupInfoItem.class);
                if(null != item.msg ){
                    tv_info.setText(item.msg);
                }
            }else if(COMMENT_TAG.equals(requestTag)){
                CommentItem commentItem = GsonUtils.jsonToClass(result,CommentItem.class);
                if(commentItem != null &&  commentItem.result){
                    listView.setAdapter(new CommentAdapter(commentItem.Comment,mActivity));
                    rb_comment.setText("评价("+commentItem.Comment.size()+")");
                }
            }else if(ADD_CART_TAG.equals(requestTag)){
                AddCartItem item = GsonUtils.jsonToClass(result,AddCartItem.class);
                if("true".equals(item.result)){
                    mActivity.showToast("加入购物车成功");
                }else {
                    mActivity.showToast(item.msg);
                }
            }else if(COLLECT_TAG.equals(requestTag)){
                CollectItem collectItem = GsonUtils.jsonToClass(result,CollectItem.class);
                if(collectItem != null){
                    isCollect = true;
                    iv_collect.setImageResource(R.mipmap.collect_confirm);
                    tv_collect.setText("已收藏");
                    if(collectItem.result){
                        mActivity.showToast("收藏成功");
                    }else {
                        mActivity.showToast(collectItem.msg);
                    }
                }

            }else if(COLLECT_CANCEL_TAG.equals(requestTag)){
                CollectItem collectItem = GsonUtils.jsonToClass(result,CollectItem.class);
                if(collectItem != null){
                    isCollect = false;
                    iv_collect.setImageResource(R.mipmap.collect_cancel);
                    tv_collect.setText("收藏");
                    if(collectItem.result){
                        mActivity.showToast("取消收藏");
                    }else {
                        mActivity.showToast(collectItem.msg);
                    }
                }
            }else if(SHOW_COLLECT.equals(requestTag)){
                CollectItem collectItem = GsonUtils.jsonToClass(result,CollectItem.class);
                if(collectItem != null){
                    isCollect = true;
                    iv_collect.setImageResource(R.mipmap.collect_confirm);
                    tv_collect.setText("已收藏");
                }
            }

        }

    }

    @Override
    public String getFragmentTitle() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_minus:
                if(count > 1){
                    count-=1;
                    tv_count.setText(count+"");
                }
                break;
            case R.id.iv_add:
                count = Integer.parseInt(tv_count.getText().toString());
                if(count < sell_count){
                    count+=1;
                    tv_count.setText(count+"");
                }
                break;
        }
    }
}
