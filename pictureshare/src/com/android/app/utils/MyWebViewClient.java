package com.android.app.utils;

import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {
	public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
	}

}
