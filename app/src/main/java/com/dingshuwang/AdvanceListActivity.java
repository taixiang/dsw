package com.dingshuwang;

import android.content.Intent;

import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.BaseTitleActivity;
import com.dingshuwang.fragment.AdvanceListFragment;

/**
 * Created by tx on 2017/6/20.
 */

public class AdvanceListActivity extends BaseTitleActivity {

    public static void actionAdvance(BaseActivity activity,String advance){
        Intent intent = new Intent(activity,AdvanceListActivity.class);
        intent.putExtra("advance",advance);
        activity.startActivity(intent);
    }

    @Override
    public CharSequence getActivityTitle() {
        return "搜索结果";
    }

    @Override
    public BaseFragment fragmentAsView() {
        return AdvanceListFragment.newInstance(getIntent().getStringExtra("advance"));
    }
}
