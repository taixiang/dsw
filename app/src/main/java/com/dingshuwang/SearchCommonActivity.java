package com.dingshuwang;

import android.content.Intent;

import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.BaseNoTitleActivity;
import com.dingshuwang.fragment.SearchCommonFragment;

/**
 * Created by tx on 2017/6/14.
 */

public class SearchCommonActivity extends BaseNoTitleActivity {

    public static void actionSearchCommon(BaseActivity activity){
        Intent intent = new Intent(activity,SearchCommonActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public CharSequence getActivityTitle() {
        return null;
    }

    @Override
    public BaseFragment fragmentAsView() {
        return SearchCommonFragment.newInstance();
    }
}
