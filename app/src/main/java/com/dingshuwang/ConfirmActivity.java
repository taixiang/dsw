package com.dingshuwang;

import android.content.Intent;
import android.util.Log;

import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.BaseTitleActivity;
import com.dingshuwang.fragment.ConfirmFragment;

/**
 * Created by tx on 2017/6/19.
 */

public class ConfirmActivity extends BaseTitleActivity {

    public static void actConfirm(BaseActivity activity, String item, String allMoney){
        Intent intent = new Intent(activity,ConfirmActivity.class);
        intent.putExtra("goods",item);
        intent.putExtra("money",allMoney);
        Log.i("》》》》 ","money 33"+allMoney);
        activity.startActivity(intent);
    }

    @Override
    public CharSequence getActivityTitle() {
        return "订单确认";
    }

    @Override
    public BaseFragment fragmentAsView() {
        String item =  getIntent().getStringExtra("goods");
        String all = getIntent().getStringExtra("money");
        Log.i("》》》》 ","money 444"+all);
        return ConfirmFragment.newInstance(item,all);
    }
}
