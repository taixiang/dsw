package com.dingshuwang;

import android.content.Intent;

import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.BaseTitleActivity;
import com.dingshuwang.fragment.CollectListFragment;

/**
 * Created by tx on 2017/6/24.
 */

public class CollectListActivity extends BaseTitleActivity {

    public static void actCollectList(BaseActivity activity){
        Intent intent = new Intent(activity,CollectListActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public CharSequence getActivityTitle() {
        return "我的收藏";
    }

    @Override
    public BaseFragment fragmentAsView() {
        return CollectListFragment.newInstance();
    }
}
