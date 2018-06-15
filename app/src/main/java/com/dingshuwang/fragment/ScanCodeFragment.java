package com.dingshuwang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dingshuwang.APIURL;
import com.dingshuwang.Constants;
import com.dingshuwang.DataView;
import com.dingshuwang.R;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.event.IsbnCodeEvent;
import com.dingshuwang.model.ScanItem;
import com.dingshuwang.util.GlideImgManager;
import com.dingshuwang.util.GsonUtils;
import com.dingshuwang.util.RequestUtils;
import com.dingshuwang.util.UIUtil;
import com.dingshuwang.view.ScanCodeView;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 扫码isbn
 *
 * @author tx
 * @date 2018/6/15
 */
public class ScanCodeFragment extends BaseFragment implements DataView {

    public static String code;

    @Bind(R.id.container)
    LinearLayout container;

    public static ScanCodeFragment newInstance(String isbn) {
        ScanCodeFragment fragment = new ScanCodeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("isbn", isbn);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        container.removeAllViews();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isbnCode(IsbnCodeEvent event) {
        String code = event.getCode();
        doGetIsbn(code);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scancode, null);
    }

    @OnClick(R.id.btn_ok)
    void cammer() {
        if (UIUtil.isfastdoubleClick()) {
            return;
        }

//        doGetIsbn("9787810666398");
        Intent openCameraIntent = new Intent(mActivity, CaptureActivity.class);
        openCameraIntent.putExtra("tag_code", 4);
        mActivity.startActivityForResult(openCameraIntent, Constants.CODE_ISBN);
    }

    private void doGetIsbn(String isbn_id) {
        String url = String.format(APIURL.SCANCODE, isbn_id);
        RequestUtils.getDataFromUrl(mActivity, url, this, "", false, false);
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag) {

    }

    @Override
    public void onGetDataSuccess(String result, String requestTag) {
        if (result != null) {
            ScanItem item = GsonUtils.jsonToClass(result, ScanItem.class);
            if ("true".equals(item.getResult())) {
                ScanCodeView view = new ScanCodeView(mActivity);
                view.setData(mActivity,item);
                container.addView(view);
            } else {
                TextView tv = new TextView(mActivity);
                tv.setText(item.getMessage());
                container.addView(tv);
            }
        }
    }

    @Override
    public String getFragmentTitle() {
        return "";
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("》》》》》===========  ", " requestCode" + requestCode + "resultCode   " + resultCode);

    }

}
