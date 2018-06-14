package com.dingshuwang;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.BaseNoTitleActivity;

public class WebViewAct extends BaseNoTitleActivity {

    private WebView contentWebView = null;
    private ImageView imgShare;
    private RelativeLayout rlyBack;

    public static void actionWebView(BaseActivity activity, String title, String url) {
        Intent intent = new Intent(activity, WebViewAct.class);
        intent.putExtra("h_url", url);
        intent.putExtra("h_title", title);
        activity.startActivity(intent);
    }

    String url = "";
    private TextView bar_title_txt;

    @SuppressLint("JavascriptInterface")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_help);
        try {
            url = this.getIntent().getStringExtra("h_url");
            String title = this.getIntent().getStringExtra("h_title");
            contentWebView = (WebView) findViewById(R.id.webview);
            bar_title_txt = (TextView) findViewById(R.id.bar_title_txt);
            rlyBack = (RelativeLayout) findViewById(R.id.rlyBack);
            imgShare = (ImageView) findViewById(R.id.img_right);
            imgShare.setOnClickListener(btnClickListener);
            imgShare.setVisibility(View.GONE);
            rlyBack.setOnClickListener(btnClickListener);
            setTitleBarText(title);
            // 启用javascript
            WebSettings settings = contentWebView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);

            contentWebView.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageStarted(
                        WebView view, String url, Bitmap favicon
                ) {

                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public void onPageFinished(WebView view, String url) {

                    super.onPageFinished(view, url);
                }
            });

            contentWebView.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int newProgress) {
                    // activity的进度是0 to 10000 (both inclusive),所以要*100
                    WebViewAct.this.setProgress(newProgress * 100);
                }
            });
            contentWebView.loadUrl(url);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setTitleBarText(String title) {
        bar_title_txt.setText(title);
    }

    @Override
    public BaseFragment fragmentAsView() {
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    OnClickListener btnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rlyBack:
                    jumpBack();
                    break;
                case R.id.img_right:
                    contentWebView.loadUrl("javascript:getInfo()");// android调用当前页面的androidGetInfo()方法。
                    break;
            }
        }
    };

    @Override
    public CharSequence getActivityTitle() {
        return null;
    }
}