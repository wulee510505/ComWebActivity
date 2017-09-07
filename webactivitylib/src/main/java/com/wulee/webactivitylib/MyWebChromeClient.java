package com.wulee.webactivitylib;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * 标题和进度条等监听
 */

public class MyWebChromeClient extends WebChromeClient {

    WebFragment.OnWebViewChangeListener mWebViewChangeListener;

    public MyWebChromeClient(WebFragment.OnWebViewChangeListener webViewChangeListener) {
        mWebViewChangeListener = webViewChangeListener;
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        if (mWebViewChangeListener != null) {
            mWebViewChangeListener.onWebViewTitleChanged(title);
        }
        super.onReceivedTitle(view, title);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (mWebViewChangeListener != null) {
            mWebViewChangeListener.onWebViewProgressChanged(newProgress);
        }
        super.onProgressChanged(view, newProgress);
    }
}
