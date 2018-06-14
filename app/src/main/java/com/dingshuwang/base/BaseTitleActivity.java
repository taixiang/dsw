package com.dingshuwang.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.dingshuwang.R;


/**
 * 中间显示一个标题的Activity的基类，标题的名称为{@link BaseActivity#getActivityTitle()}
 */
public abstract class BaseTitleActivity extends BaseActivity
{
    private BaseFragment mBaseFragment;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_as_view_common);
        mBaseFragment = fragmentAsView();
        if (null != mBaseFragment)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, mBaseFragment).commit();
            setCenterTitle(getActivityTitle(), Color.WHITE);
        }
        showTitleContainer();
        afterOnCreate(savedInstanceState);
    }

    public void afterOnCreate(Bundle savedInstanceState)
    {
//        setSwipeBackEnable(false);
    }

    public abstract BaseFragment fragmentAsView();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (null != mBaseFragment)
        {
            mBaseFragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
