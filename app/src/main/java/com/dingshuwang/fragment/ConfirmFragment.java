package com.dingshuwang.fragment;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dingshuwang.APIURL;
import com.dingshuwang.AddressListActivity;
import com.dingshuwang.Constants;
import com.dingshuwang.DataView;
import com.dingshuwang.PayActivity;
import com.dingshuwang.R;
import com.dingshuwang.adapter.CouponsAdapter;
import com.dingshuwang.adapter.OrderImageAdapter;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.model.AddressListItem;
import com.dingshuwang.model.CouponsItem;
import com.dingshuwang.model.OrderItem;
import com.dingshuwang.model.ShopCartItem;
import com.dingshuwang.model.SupplierItem;
import com.dingshuwang.model.UserItem;
import com.dingshuwang.util.GsonUtils;
import com.dingshuwang.util.RequestUtils;
import com.dingshuwang.util.StringUtils;
import com.dingshuwang.util.UIUtil;
import com.dingshuwang.view.CustomGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by tx on 2017/6/19.
 */

public class ConfirmFragment extends BaseFragment implements DataView{

    //地址
    public static final String GET_ADDRESS = "get_address_list";

    //配送费
    public static final String Supplier_URL= "supplier_url";

    //积分
    public static final String USER_INFO = "USER_INFO";

    //下单
    public static final String PAY_TAG = "pay_tag";



    @Bind(R.id.tv_add_address)
    TextView tv_add_address;
    @Bind(R.id.container)
    LinearLayout container;
    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.tv_phone)
    TextView tv_phone;
    @Bind(R.id.tv_address)
    TextView tv_address;

    @Bind(R.id.gridView)
    CustomGridView gridView;

    @Bind(R.id.tv_goods_num)
    TextView tv_goods_num;
    @Bind(R.id.tv_price)
    TextView tv_price;
    @Bind(R.id.tv_exp_price)
    TextView tv_exp_price;

    @Bind(R.id.rg_supplier)
    RadioGroup rg;

    @Bind(R.id.exp_shunf)
    RadioButton exp_shunf;

    @Bind(R.id.exp_putong)
    RadioButton exp_putong;

    @Bind(R.id.exp_youzheng)
    RadioButton exp_youzheng;

    @Bind(R.id.tv_point)
    TextView tv_point;
    @Bind(R.id.tv_coupons)
    TextView tv_coupons;
    @Bind(R.id.ll_coupons)
    LinearLayout ll_coupons;
    @Bind(R.id.tv_total_price)
    TextView tv_total_price;

    @Bind(R.id.rg_orderType)
    RadioGroup rg_orderType;
    @Bind(R.id.order_common)
    RadioButton order_common;
    @Bind(R.id.order_share)
    RadioButton order_share;

    private String address_id;

    private String money;
    private String item;
    private ShopCartItem cartItem;
    private double point;
    private String coupons;
    private String innerid = "";
    private String area_id = "";
    private String supplier_id = "";
    private String order_type = "1";

    public static ConfirmFragment newInstance(String item,String allMoney){
        ConfirmFragment fragment = new ConfirmFragment();
        Bundle bundle = new Bundle();
        bundle.putString("goods",item);
        bundle.putString("money",allMoney);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_confirm,null);
    }


    @Override
    public void onResume() {
        super.onResume();
        doGetAddres();

    }

    @OnClick(R.id.address)
    void addAddress(){
        if(UIUtil.isfastdoubleClick()){
            return;
        }
        AddressListActivity.actionAddresList(mActivity,0);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        item = (String) getArguments().get("goods");
        money= (String) getArguments().get("money");
        Log.i("》》》》 ","money 22=="+money);
        if(null != item){
            ShopCartItem item1 = GsonUtils.jsonToClass(item,ShopCartItem.class);

            supplier_id =  item1.shops.get(0).supplier_id;
            List<String> imgs = new ArrayList<>();
            for (ShopCartItem.ShopsItem shopsItem : item1.shops){
                for(ShopCartItem.ShopItem shopItem : shopsItem.Shop){
                    imgs.add(shopItem.img_url);
                }
            }
            tv_goods_num.setText("共"+imgs.size()+"件");
            tv_price.setText(money);
            gridView.setAdapter(new OrderImageAdapter(mActivity,imgs));
        }




                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(exp_shunf.getId() == checkedId){
                    getSupplier("1");
                    exp = "1";
                    express = "1";
                }else if(exp_putong.getId() == checkedId){
                    getSupplier("2");
                    exp = "2";
                    express = "6";
                }else {
                    getSupplier("3");
                    exp = "3";
                    express = "11";
                }
            }
        });
        rg_orderType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(order_common.getId() == checkedId){
                    order_type = "1";
                }else {
                    order_type = "2";
                }
            }
        });

    }

    private String exp="2";
    private  String express="6";
    private List<CouponsItem.Coupon> strs = new ArrayList<>();

    PopupWindow popupWindow ;
    private void showPop(){
        if(null != popupWindow){
            popupWindow.dismiss();
        }
        View view = LayoutInflater.from(mActivity).inflate(R.layout.popup_listview,null);
        popupWindow = new PopupWindow(view,ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow.setContentView(view);
        ListView listView = (ListView) view.findViewById(R.id.listview);
        listView.setAdapter(new CouponsAdapter(strs,mActivity));
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(ll_coupons);
//        popupWindow.showAtLocation(ll_coupons, Gravity.CENTER,0,0);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv_coupons.setText(strs.get(position).special_price);
                innerid = strs.get(position).innerid;
                if(null != popupWindow){
                    popupWindow.dismiss();
                }
            }
        });
    }

    @OnClick(R.id.ll_coupons)
    void showCoupons(){
        if(strs != null && strs.size() > 0){
            if(null != popupWindow){
                popupWindow.dismiss();
                popupWindow=null;
                return;
            }
            showPop();
        }
    }

    @OnClick(R.id.post)
    public void confirm(){
        if(UIUtil.isfastdoubleClick()){
            return;
        }
        try {
            if(exp.length() > 0 && express.length()>0){
                doGetConfirm(exp,express);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doGetAddres(){
        String url = String.format(APIURL.ADDRESS_LIST, Constants.USER_ID);
        RequestUtils.getDataFromUrl(mActivity,url,this,GET_ADDRESS,false,false);
    }

    //配送费
    private void getSupplier(String type){
        String url = String.format(APIURL.Supplier_URL,supplier_id,type,area_id,Constants.USER_ID);
        RequestUtils.getDataFromUrl(mActivity,url,this,Supplier_URL,false,false);
    }

    //积分
    private void doGetMsg(){
        String url = String.format(APIURL.USER_INFO_URL, Constants.USER_ID);
        RequestUtils.getDataFromUrlByPost(mActivity,url,"",this,USER_INFO,false,false);
    }

    //优惠券
    private void doGetCoupons(){
        String url = String.format(APIURL.coupons_url, Constants.USER_ID);
        RequestUtils.getDataFromUrl(mActivity,url,this,"coupons_url",false,false);
    }

    //下单
    private void doGetConfirm(String exp_id ,String express_id){
        String url = String.format(APIURL.ConfirmOrder_url,Constants.USER_ID,address_id,exp_id,express_id,"",tv_point.getText().toString(),"","",order_type);
        RequestUtils.getDataFromUrl(mActivity,url,this,PAY_TAG,false,false);
    }


    @Override
    public void onGetDataFailured(String msg, String requestTag) {

    }

    @Override
    public void onGetDataSuccess(String result, String requestTag) {
        if(result != null){
            if(GET_ADDRESS.equals(requestTag)){
                tv_add_address.setVisibility(View.VISIBLE);
                container.setVisibility(View.GONE);
                AddressListItem item = GsonUtils.jsonToClass(result,AddressListItem.class);
                if(null != item && item.address != null && item.address.size()>0){
                    for(AddressListItem.AddressItem addressItem : item.address){
                        if(addressItem.is_default.equals("1")){
                            address_id = addressItem.id;
                            area_id = addressItem.area_id;
                            tv_add_address.setVisibility(View.GONE);
                            container.setVisibility(View.VISIBLE);
                            tv_name.setText(addressItem.accept_name);
                            tv_phone.setText(addressItem.phone);
                            tv_address.setText(addressItem.area+addressItem.address);
                            doGetMsg();
                        }
                    }
                }
            }else if(Supplier_URL.equals(requestTag)){
                SupplierItem item  = GsonUtils.jsonToClass(result,SupplierItem.class);
                tv_exp_price.setText(item.fright+"元");
                Log.i("》》》》》   ","moneyTotal === "+ Double.parseDouble(money) +"  fright ==  "+ Double.parseDouble(item.fright) + "  point== "+point/100);
               double total =   Double.parseDouble(money) + Double.parseDouble(item.fright) - Double.parseDouble(StringUtils.doubleFormat(point/100)) ;

                tv_total_price.setText(total+"元");
            }else if(USER_INFO.equals(requestTag)){
                UserItem userItem = GsonUtils.jsonToClass(result,UserItem.class);
                if(null != userItem){
                    point = userItem.point;
                    String point_money = StringUtils.doubleFormat(point / 100);
                    tv_point.setText(point_money+"");
                    getSupplier("2");
                }
            }else if("coupons_url".equals(requestTag)){
                CouponsItem couponsItem = GsonUtils.jsonToClass(result,CouponsItem.class);
                if(couponsItem != null && couponsItem.coupons != null && couponsItem.coupons.size()>0){
                    coupons = couponsItem.coupons.get(0).special_price;
                    tv_coupons.setText(coupons);
                    strs = couponsItem.coupons;
                    innerid = couponsItem.coupons.get(0).innerid;
                }
            }else if(PAY_TAG.equals(requestTag)){

                OrderItem orderItem = GsonUtils.jsonToClass(result,OrderItem.class);
                if(null != orderItem){
                    if(orderItem.result.equals("true")){
                        PayActivity.actConfirm(mActivity,orderItem.message);
                        mActivity.finish();
                    }else {
                        mActivity.showToast(orderItem.message);
                    }
                }
            }
        }


    }

    @Override
    public String getFragmentTitle() {
        return null;
    }
}
