package com.dingshuwang;

import android.content.Intent;

import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.BaseTitleActivity;
import com.dingshuwang.fragment.AddressListFragment;

/**
 * Created by tx on 2017/6/19.
 */

public class AddressListActivity extends BaseTitleActivity {

    public static void actionAddresList(BaseActivity activity ,int tag){
        Intent intent = new Intent(activity,AddressListActivity.class);
        intent.putExtra("tag",tag);
        activity.startActivity(intent);
    }

    @Override
    public CharSequence getActivityTitle() {
        return "收货地址";
    }

    @Override
    public BaseFragment fragmentAsView() {
        return AddressListFragment.newInstance(getIntent().getIntExtra("tag",0));
    }
}
