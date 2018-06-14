package com.dingshuwang;

import android.content.Intent;

import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.BaseNoTitleActivity;
import com.dingshuwang.base.BaseTitleActivity;
import com.dingshuwang.fragment.KeywordsListFragment;

/**
 * Created by tx on 2017/6/9.
 */

public class KeywordsListActivity extends BaseTitleActivity {

    public static void actionKeywordsList(BaseActivity activity,String keyword){
        Intent intent = new Intent(activity,KeywordsListActivity.class);
        intent.putExtra("keyword",keyword);
        activity.startActivity(intent);
    }

    @Override
    public CharSequence getActivityTitle() {
        return "";
    }

    @Override
    public BaseFragment fragmentAsView() {

        return KeywordsListFragment.newInstance(getIntent().getStringExtra("keyword"));
    }
}
