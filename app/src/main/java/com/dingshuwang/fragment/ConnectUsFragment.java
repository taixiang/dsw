package com.dingshuwang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dingshuwang.R;
import com.dingshuwang.base.BaseFragment;

/**
 * Created by tx on 2017/6/28.
 */

public class ConnectUsFragment extends BaseFragment {

    public static ConnectUsFragment newInstance(){
        return new ConnectUsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_connect_us,null);
    }

    @Override
    public String getFragmentTitle() {
        return null;
    }
}
