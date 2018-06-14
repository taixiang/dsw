package com.dingshuwang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dingshuwang.APIURL;
import com.dingshuwang.AddressEditActivity;
import com.dingshuwang.Constants;
import com.dingshuwang.DataView;
import com.dingshuwang.R;
import com.dingshuwang.adapter.AddressListAdapter;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.model.AddressListItem;
import com.dingshuwang.util.GsonUtils;
import com.dingshuwang.util.RequestUtils;
import com.dingshuwang.util.UIUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by tx on 2017/6/19.
 */

public class AddressListFragment extends BaseFragment implements DataView{

    @Bind(R.id.xListview)
    ListView listView;


    /**
     * 获取收获地址列表
     */
    public static final String GET_ADDRESS = "get_address_list";

    private int tag ;

    public static AddressListFragment newInstance(int tag){
        AddressListFragment fragment = new AddressListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tag",tag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_addresslist,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tag =  getArguments().getInt("tag",0);

    }

    @Override
    public void onResume() {
        super.onResume();
        doGetAddres();
    }

    @OnClick(R.id.ll_add)
    void add_address(){
        if(UIUtil.isfastdoubleClick()){
            return;
        }
        AddressEditActivity.actionAddressEdit(mActivity,"-1");
    }

    private void doGetAddres(){
        String url = String.format(APIURL.ADDRESS_LIST,Constants.USER_ID);
        RequestUtils.getDataFromUrl(mActivity,url,this,GET_ADDRESS,false,false);
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag) {

    }

    @Override
    public void onGetDataSuccess(String result, String requestTag) {
        if(result != null){
            if(GET_ADDRESS.equals(requestTag)){
                AddressListItem item = GsonUtils.jsonToClass(result,AddressListItem.class);
                if(item != null){
                    listView.setAdapter(new AddressListAdapter(mActivity,item.address,tag));
                }
            }
        }

    }

    @Override
    public String getFragmentTitle() {
        return null;
    }
}
