package com.accentrs.iofferbh.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.webkit.WebView;

import com.accentrs.iofferbh.R;


public class PrivacyPolicyActivity extends BaseActivity {

    private WebView webView;
    private String url = "file:///android_asset/privacy-policy.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        initializeViews();
        setWebView();
    }

    private void initializeViews() {
        setIofferBhLogo();
        setSearchIconVisibility(false);
        setLeftIconDrawableVisibility(true);
        setLeftIconDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back));
        webView = (WebView) findViewById(R.id.wv_privacy_policy);
    }


    private void setWebView() {
        webView.loadUrl(url);
    }

}
