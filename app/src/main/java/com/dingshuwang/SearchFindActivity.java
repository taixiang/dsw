package com.dingshuwang;

import android.content.Intent;

import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.BaseNoTitleActivity;
import com.dingshuwang.fragment.SearchFindFragment;

/**
 * Created by tx on 2017/6/14.
 */

public class SearchFindActivity extends BaseNoTitleActivity {

    public static void actionSearchFind(BaseActivity activity){
        Intent intent = new Intent(activity,SearchFindActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public CharSequence getActivityTitle() {
        return null;
    }

    @Override
    public BaseFragment fragmentAsView() {
        return SearchFindFragment.newInstance();
    }
}
