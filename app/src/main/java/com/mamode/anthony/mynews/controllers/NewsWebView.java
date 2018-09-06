package com.mamode.anthony.mynews.controllers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mamode.anthony.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsWebView extends Activity {
    @BindView(R.id.webview)
    WebView webview;

    @SuppressLint("setJavaScriptEnabled")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Prepare the progress bar.
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.news_web_view);
        ButterKnife.bind(this);

        // Configure the webView.
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebChromeClient(new WebChromeClient());
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);

        WebSettings webSettings = webview.getSettings();
        webSettings.setDomStorageEnabled(true);

        // Load URL.
        Intent intent = getIntent();
        webview.loadUrl(intent.getStringExtra("url"));
        // Show the progress bar.
        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                setProgress(progress * 100);
            }
        });
        // Call private class InsideWebViewClient.
        webview.setWebViewClient(new InsideWebViewClient());
    }

    private class InsideWebViewClient extends WebViewClient {
        @Override
        // Force links to be opened inside WebView and not in Default Browser.
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}