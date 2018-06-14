package com.dingshuwang;

import android.content.Intent;

import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.BaseTitleActivity;
import com.dingshuwang.fragment.AddressEditFragment;

/**
 * Created by tx on 2017/6/19.
 */

public class AddressEditActivity extends BaseTitleActivity {

    public static void actionAddressEdit(BaseActivity activity,String address_id){
        Intent intent = new Intent(activity,AddressEditActivity.class);
        intent.putExtra("address_id",address_id);
        activity.startActivity(intent);
    }

    @Override
    public CharSequence getActivityTitle() {
        return "地址编辑";
    }

    @Override
    public BaseFragment fragmentAsView() {
        return AddressEditFragment.newInstance(getIntent().getStringExtra("address_id"));
    }
}
