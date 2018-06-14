package com.dingshuwang;

import android.os.Bundle;
import android.webkit.WebView;

import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.BaseNoTitleActivity;

import butterknife.Bind;

/**
 * Created by tx on 2017/6/20.
 */

public class TestAct extends BaseNoTitleActivity {

    WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);
        webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl("http://m.iisbn.com/rec.html");
    }

    @Override
    public CharSequence getActivityTitle() {
        return null;
    }

    @Override
    public BaseFragment fragmentAsView() {
        return null;
    }
}
