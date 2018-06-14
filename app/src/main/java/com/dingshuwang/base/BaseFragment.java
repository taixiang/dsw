package com.dingshuwang.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dingshuwang.Constants;
import com.dingshuwang.R;
import com.dingshuwang.util.BitmapUtils;
import com.dingshuwang.util.PhotoUtils;

import butterknife.ButterKnife;

/**
 * Created by raiyi-suzhou on 2015/5/11 0011.
 */
public abstract class BaseFragment extends Fragment implements IFragmentTitle {
    protected BaseActivity mActivity;
    /**
     * 一个标识值， 应该在{@link Fragment#onCreateView(LayoutInflater, ViewGroup, Bundle)}方法中将其修改为true
     */
    protected boolean mIsInit = false;
    protected boolean mCanPullToRefresh = false;
    protected BaseFragment mFragment;
    private Context mContext;
    public boolean isUseButterKnife = true;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mActivity = (BaseActivity) activity;
            mFragment = this;
            mContext = getActivity();
        } catch (Exception e) {

        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(isUseButterKnife) {
            if (null != view) {
                ButterKnife.bind(mFragment, view);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            if (mIsInit) {
                mIsInit = false;
                doGetData();
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (mIsInit) {
                mIsInit = false;
                doGetData();
            }
        }
    }

    /**
     * 在此方法中进行网络请求操作  ,注意懒加载只有在和ViewPager结合使用时才有效的
     * 如果只是一个单纯的Fragment,需要手动去调用此方法
     */
    protected void doGetData() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("》》》》  "," base requestCode ===== "+requestCode);
        Log.i("》》》》  "," base resultcode ===== "+resultCode);
        Log.i("》》》》","base --------");

        if (resultCode == Activity.RESULT_OK)
        {
            String imagePath = null;
            String imagePath2 = null;
            if (PhotoUtils.REQUEST_FROM_PHOTO == requestCode)
            {
                if (null != data && null != data.getData())
                {
                    imagePath = PhotoUtils.getFinalPhotoImagePath(mActivity, data.getData());
                }
            } else if (PhotoUtils.REQUEST_FROM_CAMERA == requestCode)
            {
                imagePath = PhotoUtils.getFinalCameraImagePath();
            }else if (PhotoUtils.REQUEST_FROM_PHOTO_2 == requestCode)
            {
                if (null != data && null != data.getData())
                {
                    imagePath2 = PhotoUtils.getFinalPhotoImagePath(mActivity, data.getData());
                }
            } else if (PhotoUtils.REQUEST_FROM_CAMERA_2 == requestCode)
            {
                imagePath2 = PhotoUtils.getFinalCameraImagePath();
            }
            if (!TextUtils.isEmpty(imagePath))
            {
                Intent intent = new Intent(Constants.PHOTO_URL);
                intent.putExtra("photo_url",imagePath);
                mActivity.sendBroadcast(intent);
            }

            if (!TextUtils.isEmpty(imagePath2))
            {
                Intent intent = new Intent(Constants.PHOTO_URL_2);
                intent.putExtra("photo_url",imagePath2);
                mActivity.sendBroadcast(intent);
            }
        }

    }

    /**
     * 结束当前activity
     */
    protected void jumpBack() {
        getActivity().finish();
    }

    /**
     * 加载下一个activity,不结束自身
     */
    protected void loadNext(Class<?> cls) {
        Intent intent = new Intent(mContext, cls);
        startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
    }

    /**
     * 加载下一个activity,不结束自身 {key,value}, {key,value}, {key,value} ...
     *
     * @param cls
     * @param pairs
     */
    protected void loadNext(Class<?> cls, String[]... pairs) {
        Intent intent = new Intent(mContext, cls);
        for (String[] pair : pairs) {
            intent.putExtra(pair[0], pair[1]);
        }
        startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
    }

    /**
     * 加载下一个activity,不结束自身,使用Result模式
     */
    protected void loadNextForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(mContext, cls);
        startActivityForResult(intent, requestCode);
        ((Activity) mContext).overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
    }

  /**
     * 加载下一个activity,不结束自身,使用Result模式
     */
    protected void loadNextForResult(Class<?> cls, int requestCode, String[]... pairs) {
        Intent intent = new Intent(mContext, cls);
        for (String[] pair : pairs) {
            intent.putExtra(pair[0], pair[1]);
        }
        startActivityForResult(intent, requestCode);
        ((Activity) mContext).overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
    }

    private ProgressDialog mProgressDialog;
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
                mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
                mProgressDialog.setContentView(R.layout.layout_progress_dialog);
            } else
            {
                mProgressDialog = new ProgressDialog(mActivity);
                mProgressDialog.show();
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

}
