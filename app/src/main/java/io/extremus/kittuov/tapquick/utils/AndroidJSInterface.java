package io.extremus.kittuov.tapquick.utils;

import android.webkit.JavascriptInterface;

/**
 * Created by kittuov on 29/4/16.
 */
public class AndroidJSInterface {
    PageLoadListner mListener;
    WebSocketListener mWSListener;


    public void setPageLoadListener(PageLoadListner listener) {
        mListener = listener;
    }

    public void setWebSocketListener(WebSocketListener listener) {
        mWSListener = listener;
    }

    public AndroidJSInterface(PageLoadListner listner) {
        setPageLoadListener(listner);
    }

    public AndroidJSInterface() {

    }

    @JavascriptInterface
    public void Loaded(String url, int status, String response) {
        if (mListener != null)
            mListener.onPageFinished(url, status, response);
    }

    @JavascriptInterface
    public void onWebMessage(String message) {

        mWSListener.OnMessage(message);
    }

    @JavascriptInterface
    public void onWebDisconnect() {
        mWSListener.OnDisconnect();
    }

}
