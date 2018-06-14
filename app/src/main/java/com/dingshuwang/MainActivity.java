package com.dingshuwang;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.BaseNoTitleActivity;
import com.dingshuwang.fragment.MainFragment;

public class MainActivity extends BaseNoTitleActivity {

    private MainFragment mainFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        register();

    }

    @Override
    public BaseFragment fragmentAsView() {
        mainFragment =MainFragment.newInstance();
        return mainFragment;
    }



    @Override
    protected void onDestroy() {

        super.onDestroy();
        try {
            mActivity.unregisterReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Constants.GOTOFIRST)){
                Log.i("》》》》  "," =============== ");
                mainFragment.toTab(0);
            }
        }
    };

    private void register(){
        IntentFilter filter = new IntentFilter(Constants.GOTOFIRST);
        mActivity.registerReceiver(receiver,filter);
    }

    @Override
    public CharSequence getActivityTitle() {
        return null;
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        Log.i("","requestCOde ====  "+requestCode);
//        Log.i("","resultCode ====  "+resultCode);
//        if(resultCode == 0){
//            mainFragment.toTab(0);
//        }
//    }
}
