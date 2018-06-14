package com.dingshuwang;

import android.content.Intent;

import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.BaseTitleActivity;
import com.dingshuwang.fragment.NovelFragment;

/**
 * Created by tx on 2017/6/15.
 */

public class NovelActivity extends BaseTitleActivity {

    public static void actionNovel(BaseActivity activity){
        Intent intent = new Intent(activity,NovelActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public CharSequence getActivityTitle() {
        return "小说专区";
    }

    @Override
    public BaseFragment fragmentAsView() {
        return NovelFragment.newInstance();
    }
}
