package com.dingshuwang.base;

import android.os.Bundle;

/**
 * 将中间显示标题的ToolBar隐藏
 */
public abstract class BaseNoTitleActivity extends BaseTitleActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID)
    {
        super.setContentView(layoutResID);
        hideTitleBar();
    }

    @Override
    public void afterOnCreate(Bundle savedInstanceState)
    {
        super.afterOnCreate(savedInstanceState);
        hideTitleBar();
    }
}
