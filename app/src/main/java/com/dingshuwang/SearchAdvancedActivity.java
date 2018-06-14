package com.dingshuwang;

import android.content.Intent;

import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.BaseTitleActivity;
import com.dingshuwang.fragment.SearchAdvancedFragment;

/**
 * Created by tx on 2017/6/19.
 */

public class SearchAdvancedActivity extends BaseTitleActivity {

    public static void actionSearchAdvaced(BaseActivity activity){
        Intent intent = new Intent(activity,SearchAdvancedActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public CharSequence getActivityTitle() {
        return "高级搜索";
    }

    @Override
    public BaseFragment fragmentAsView() {
        return SearchAdvancedFragment.newInstance();
    }
}
