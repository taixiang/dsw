package com.dingshuwang;

import android.content.Intent;

import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.BaseTitleActivity;
import com.dingshuwang.fragment.ScanCodeFragment;

/**
 * @author tx
 * @date 2018/6/15
 */
public class ScanCodeActivity extends BaseTitleActivity {

    public static void actIsbn(BaseActivity activity, String isbn){
        Intent intent = new Intent(activity,IsbnCodeActivity.class);
        intent.putExtra("isbn",isbn);
        activity.startActivity(intent);
    }

    @Override
    public BaseFragment fragmentAsView() {
        String isbn = getIntent().getStringExtra("isbn");
        return ScanCodeFragment.newInstance(isbn);
    }

    @Override
    public CharSequence getActivityTitle() {
        return "扫码ISBN";
    }
}
