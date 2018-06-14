package com.dingshuwang;

import android.content.Intent;

import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.BaseTitleActivity;
import com.dingshuwang.fragment.DetailFragment;

/**
 * Created by tx on 2017/6/14.
 */

public class DetailActivity extends BaseTitleActivity {

    public static void actionDetail(BaseActivity activity,String id){
        Intent intent = new Intent(activity,DetailActivity.class);
        intent.putExtra("id",id);
        activity.startActivity(intent);
    }

    @Override
    public CharSequence getActivityTitle() {
        return "书本详情";
    }

    @Override
    public BaseFragment fragmentAsView() {
        return DetailFragment.newInstance(getIntent().getStringExtra("id"));
    }
}
