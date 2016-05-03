package io.extremus.kittuov.tapquick.utils;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import io.extremus.kittuov.tapquick.MainActivity;

/**
 * Created by kittuov on 1/5/16.
 */
public abstract class WebSocket implements WebSocketListener {
    public WebView mWebView;

    public WebSocket(WebView wv) {
        mWebView = wv;
        MainActivity.jsInterface.setWebSocketListener(this);
    }

    public void joinRoom(String url, String room) {
        mWebView.loadUrl("javascript:joinRoom(\"" + url + "\",'" + room + "')");
    }

}
