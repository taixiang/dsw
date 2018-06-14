package com.dingshuwang.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.dingshuwang.APIURL;
import com.dingshuwang.Constants;
import com.dingshuwang.DataView;
import com.dingshuwang.R;
import com.dingshuwang.adapter.CityAdapter;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.model.AddressDetailItem;
import com.dingshuwang.model.AddressSaveItem;
import com.dingshuwang.model.CityItem;
import com.dingshuwang.util.GsonUtils;
import com.dingshuwang.util.RequestUtils;
import com.dingshuwang.util.StringUtils;
import com.dingshuwang.util.UIUtil;
import com.dingshuwang.view.CustomListView;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by tx on 2017/6/19.
 */

public class AddressEditFragment extends BaseFragment implements DataView{


    /**
     * json城市数据
     */
    public static final String city_name = "get_city_name";

    public static final String detail_address= "detail_address";

    /**
     * 保存地址
     */
    public static final String SAVE_TAG = "address_save";

    private List<CityItem> allList;
    private List<String> proList = new ArrayList<>();
    private List<CityItem.CityName> cityList ;
    private List<String> cityStr = new ArrayList<>();
    private String pro,cityName,address_id,area_id;

    @Bind(R.id.name_edit)
    EditText name_edit;
    @Bind(R.id.phone_edit)
    EditText phone_edit;
    @Bind(R.id.city_choose)
    EditText city_choose;
    @Bind(R.id.address_edit)
    EditText address_edit;



    private AlertDialog proDialog;
    private AlertDialog cityDialog;

    public static AddressEditFragment newInstance(String addres_id){
        AddressEditFragment fragment = new AddressEditFragment();
        Bundle bundle = new Bundle();
        bundle.putString("address_id",addres_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void doGetCity(){
        String url = APIURL.CITY_URL;
        RequestUtils.getDataFromUrl(mActivity,url,this,city_name,false,false);
    }

    private void doGetDetailAddress(String address_id){
        String url = String.format(APIURL.ADDRESS_INFO_URL, Constants.USER_ID,address_id);
        RequestUtils.getDataFromUrl(mActivity,url,this,detail_address,false,false);
    }

    //新增地址
    private void doSaveAddress(String id,String name,String area_id,String address,String phone){
        String url = String.format(APIURL.ADD_ADDRESS,id,name,area_id,address,phone);
        RequestUtils.getDataFromUrl(mActivity,url,this,SAVE_TAG,false,false);
    }

    //编辑地址
    private void doEditAddress(String id,String name,String area_id,String address,String phone,String address_id){
        String url = String.format(APIURL.EDIT_ADDRESS,id,address_id,name,area_id,address,phone);
        RequestUtils.getDataFromUrl(mActivity,url,this,SAVE_TAG,false,false);
    }

    @OnClick(R.id.city_choose)
    void cityChoose(){
        if(UIUtil.isfastdoubleClick()){
            return;
        }

        if(null != proDialog){
            proDialog = null;
        }
        proDialog = new AlertDialog.Builder(mActivity).show();
        final View view = LayoutInflater.from(mActivity).inflate(R.layout.pop_up_listview, null);
        proDialog.setContentView(view);
        proDialog.setCanceledOnTouchOutside(true);
        Window window = proDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        CustomListView listView = (CustomListView) view.findViewById(R.id.listview);
        listView.setAdapter(new CityAdapter(proList,mActivity));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View convertView, int position, long id) {
                cityList = allList.get(position).City;
                Log.i("》》》》","cityList  "+cityList.toString());
                pro = proList.get(position);
                cityStr.clear();
                for(CityItem.CityName cityName : cityList){
                    cityStr.add(cityName.city_name);
                }
                Log.i("》》》》","cityStr  "+cityStr.toString());

                if(null != cityDialog){
                    cityDialog = null;
                }
                cityDialog = new AlertDialog.Builder(mActivity).show();
                final View view = LayoutInflater.from(mActivity).inflate(R.layout.pop_up_listview, null);
                cityDialog.setContentView(view);
                cityDialog.setCanceledOnTouchOutside(true);
                Window window = cityDialog.getWindow();
                window.setGravity(Gravity.CENTER);
                CustomListView listView = (CustomListView) view.findViewById(R.id.listview);
                listView.setAdapter(new CityAdapter(cityStr,mActivity));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        cityName = cityStr.get(position);
                        area_id = cityList.get(position).id;
                        city_choose.setText(pro+" "+cityName);


                        if(proDialog != null){
                            proDialog.dismiss();
                        }
                        if(cityDialog != null){
                            cityDialog.dismiss();
                        }


                    }
                });

            }
        });
    }

    @OnClick(R.id.confirm)
    void confirm(){
        if(UIUtil.isfastdoubleClick()){
            return;
        }
        String nameStr = name_edit.getText().toString().trim();
        String mobileStr = phone_edit.getText().toString().trim();
        String streetStr = address_edit.getText().toString().trim();
        String cityStr = city_choose.getText().toString().trim();
        if(StringUtils.isEmpty(nameStr)){
            mActivity.showToast("请输入姓名");
            return;
        }
        if(StringUtils.isEmpty(mobileStr)){
            mActivity.showToast("请输入电话");
            return;
        }
        if(StringUtils.isEmpty(cityStr)){
            mActivity.showToast("请选择省市");
            return;
        }
        if(StringUtils.isEmpty(streetStr)){
            mActivity.showToast("请输入详细地址");
            return;
        }
        if(!"-1".equals(address_id)){
            doEditAddress(Constants.USER_ID,nameStr,area_id,streetStr,mobileStr,address_id);
        }else {
            doSaveAddress(Constants.USER_ID,nameStr,area_id,streetStr,mobileStr);
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        doGetCity();
        address_id = getArguments().getString("address_id");
        if(!"-1".equals(address_id)){
            doGetDetailAddress(address_id);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_address_edit,null);
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag) {

    }

    @Override
    public void onGetDataSuccess(String result, String requestTag) {
        if(result != null){
            if(detail_address.equals(requestTag)){
                AddressDetailItem detailItem = GsonUtils.jsonToClass(result,AddressDetailItem.class);
                if(null != detailItem  ){
                    AddressDetailItem.Item item = detailItem.address;
                    if(null != item){
                        name_edit.setText(item.accept_name);
                        phone_edit.setText(item.phone);
                        city_choose.setText(item.area);
                        address_edit.setText(item.address);
                    }
                }
            }else if(city_name.equals(requestTag)){
                allList =GsonUtils.jsonToList(result,new TypeToken<List<CityItem>>(){}.getType());
                proList.clear();
                for(CityItem cityItem : allList){
                    proList.add(cityItem.province_name);
                }
            }else if(SAVE_TAG.equals(requestTag)){
                AddressSaveItem item = GsonUtils.jsonToClass(result,AddressSaveItem.class);
                mActivity.showToast(item.info);
                if("y".equals(item.status)){
                    mActivity.finish();
                }

            }
        }

    }

    @Override
    public String getFragmentTitle() {
        return null;
    }
}
