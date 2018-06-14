package com.dingshuwang.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dingshuwang.R;

import butterknife.ButterKnife;

/**
 * 使用到的基类Activity
 */
public abstract class BaseActivity extends AppCompatActivity implements IActivityTitle
{
    protected static BaseActivity mActivity;
    private ProgressDialog mProgressDialog;
    private FrameLayout mFrameContainer;
    private TextView mTvCenterTitle;
    private TextView mTvRightTitle;
    private RelativeLayout mRelativeTitleContainer;
    private ImageView mIvRight;
    private ImageView mIvBack;
    public SharedPreferences mPreferences;
    private Toast mCurrentToast;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mPreferences = mActivity.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        ButterKnife.bind(mActivity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

    }

    public SharedPreferences getPreferences()
    {
        return mPreferences;
    }

    @Override
    public void setContentView(int layoutResID)
    {
        View contentView = LayoutInflater.from(mActivity).inflate(layoutResID, null);
        setContentView(contentView);
    }

    @Override
    public void setContentView(View view)
    {
        super.setContentView(R.layout.activity_base);
        mFrameContainer = (FrameLayout) findViewById(R.id.frame_container);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        mFrameContainer.addView(view, layoutParams);
        initTitleBar();
    }

    /***
     * 初始化title bar
     */
    private void initTitleBar()
    {
        mTvCenterTitle = (TextView) findViewById(R.id.tv_center_title);
        mTvRightTitle = (TextView) findViewById(R.id.tv_right_title);
        mIvRight = (ImageView) findViewById(R.id.iv_right);
        mRelativeTitleContainer = (RelativeLayout) findViewById(R.id.relative_title_container);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
    }

    public void hideTitleBar()
    {
        if (null != mRelativeTitleContainer)
        {
            mRelativeTitleContainer.setVisibility(View.GONE);
        }
        View viewViewLine = findViewById(R.id.view_view_line);
        if (null != viewViewLine)
        {
            viewViewLine.setVisibility(View.GONE);
        }
    }

    public void showTitleBar()
    {
        if (null != mRelativeTitleContainer)
        {
            mRelativeTitleContainer.setVisibility(View.VISIBLE);
        }
        View viewViewLine = findViewById(R.id.view_view_line);
        if (null != viewViewLine)
        {
            viewViewLine.setVisibility(View.VISIBLE);
        }

    }

    protected void showTitleContainer()
    {
        if (null != mRelativeTitleContainer)
        {
            mRelativeTitleContainer.setVisibility(View.VISIBLE);
            mIvBack.setVisibility(View.VISIBLE);
            int homeIndicatorResId = R.mipmap.ic_back;
            mIvBack.setImageResource(homeIndicatorResId);
            mIvBack.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                        onBackPressed();
                }
            });
        }
    }

    public void setOnBackClickListener(int backResId, View.OnClickListener onClickListener)
    {
        if (null != mIvBack)
        {
            if (mIvBack.getVisibility() == View.GONE)
            {
                mIvBack.setVisibility(View.VISIBLE);
            }
            if (backResId != 0)
            {
                mIvBack.setImageResource(backResId);
            }
            mIvBack.setOnClickListener(onClickListener);
        }
    }

    /***
     * 隐藏标题栏左侧的返回imageview
     */
    public void hideBackImageView()
    {
        if (mIvBack != null && mIvBack.getVisibility() == View.VISIBLE)
        {
            mIvBack.setVisibility(View.GONE);
        }
    }

    /***
     * 控制标题栏右侧的imageview是否显示
     *
     * @param isVisibility true：显示 、false 隐藏
     */
    public void setRightImageViewVisibility(boolean isVisibility)
    {
        if (mIvRight != null)
        {
            if (isVisibility)
            {
                mIvRight.setVisibility(View.VISIBLE);
            } else
            {
                mIvRight.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        hideSoftKeyBorard();

        super.onBackPressed();
    }

    public void setOnRightClickListener(int rightResId, View.OnClickListener onClickListener)
    {
        if (null != mIvRight)
        {
            if (mIvRight.getVisibility() == View.GONE)
            {
                mIvRight.setVisibility(View.VISIBLE);
            }
            if (rightResId != 0)
            {
                mIvRight.setImageResource(rightResId);
            }
            mIvRight.setOnClickListener(onClickListener);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /***
     * 显示进度对话框
     */
    public void showProgressDialog()
    {
        showProgressDialog("请稍等，正在加载数据……");
    }

    /***
     * 显示进度对话框
     *
     * @param msg ：显示的提示文字
     */
    public void showProgressDialog(String msg)
    {
        try
        {

            if (null != mProgressDialog)
            {
                mProgressDialog = null;
                mProgressDialog = new ProgressDialog(mActivity);
                mProgressDialog.show();
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
                mProgressDialog.setContentView(R.layout.layout_progress_dialog);
            } else
            {
                mProgressDialog = new ProgressDialog(mActivity);
                mProgressDialog.show();
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
                mProgressDialog.setContentView(R.layout.layout_progress_dialog);
            }
        } catch (Exception e)
        {

        }
    }

    public void dismissProgressDialog()
    {
        try
        {
            if (null != mProgressDialog)
            {
                if (mProgressDialog.isShowing())
                {
                    mProgressDialog.dismiss();
                }
                mProgressDialog = null;
            }
        } catch (Exception e)
        {

        }
    }

    public void setCenterTitle(CharSequence centerTitle)
    {
        mTvCenterTitle.setText(centerTitle);
    }

    public void setCenterTitle(CharSequence centerTitle, int textColor)
    {
        mTvCenterTitle.setTextColor(textColor);
        mTvCenterTitle.setText(centerTitle);
    }

    public void setRightTitleWithClickWithColor(CharSequence rightTitle, int textColor, View.OnClickListener onClickListener)
    {
        if (null != mTvRightTitle)
        {
            mTvRightTitle.setVisibility(View.VISIBLE);
            mTvRightTitle.setTextColor(textColor);
            mTvRightTitle.setText(rightTitle);
            mTvRightTitle.setOnClickListener(onClickListener);

        }
    }

    /**
     * 隐藏软键盘，根据当前焦点View
     */
    public void hideSoftKeyBorard()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive())
        {
            if (imm.isActive())
            {
                View focusView = mActivity.getCurrentFocus();
                if (null != focusView)
                {
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);

                }
            }
        }
    }

    /**
     * 隐藏软键盘，根据给定的View
     *
     * @param view
     */
    public void hideSoftKeyBorard(View view)
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && null != view)
        {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showToast(String text)
    {
        if (null != mCurrentToast)
        {
            mCurrentToast.cancel();
        }
        mCurrentToast = Toast.makeText(mActivity, text, Toast.LENGTH_SHORT);
        mCurrentToast.show();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        hideSoftKeyBorard();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
////        System.out.println("mActivity = " + mActivity + " resultCode = " + resultCode + " requestCode = " + requestCode + " data = " + data);
//        super.onActivityResult(requestCode, resultCode, data);
//
//    }

    public MMApplication getMMApplication()
    {
        return (MMApplication) getApplication();
    }

//    public UserInfoItem getUserInfoItem()
//    {
//        UserInfoItem userInfoItem = null;
//        String userInfoString = mPreferences.getString(PREF.PREF_USER_INFO, null);
//        if (!TextUtils.isEmpty(userInfoString))
//        {
//            userInfoItem = GsonUtils.jsonToClass(userInfoString, UserInfoItem.class);
//        }
//        return userInfoItem;
//    }

//    public void saveUserInfoItem(UserInfoItem userInfoItem)
//    {
//        if (null != userInfoItem)
//        {
//            mPreferences.edit().putString(PREF.PREF_USER_INFO, GsonUtils.toJson(userInfoItem)).commit();
//        }
//    }

//    public String getAccessTokenItem()
//    {
//        String accessToken = mPreferences.getString(PREF.PREF_ACCESS_TOKEN, "");
//        AccessTokenItem accessTokenItem = GsonUtils.jsonToClass(accessToken, AccessTokenItem.class);
//        if (null != accessTokenItem && accessTokenItem.accessToken != null)
//        {
//        }
//        return accessToken;
//    }

    /**
     * 加载下一个activity,不结束自身
     */
    protected void loadNext(Class<?> cls)
    {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    /**
     * 加载下一个activity,不结束自身 {key,value}, {key,value}, {key,value} ...
     *
     * @param cls
     * @param pairs
     */
    public void loadNext(Class<?> cls, String[]... pairs)
    {
        Intent intent = new Intent(this, cls);
        for (String[] pair : pairs)
        {
            intent.putExtra(pair[0], pair[1]);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    /***
     * 返回
     *
     * @param cls
     */
    protected void jumpBack(Class<?> cls)
    {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    /**
     * 结束当前activity
     */
    protected void jumpBack()
    {
        finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }


}
