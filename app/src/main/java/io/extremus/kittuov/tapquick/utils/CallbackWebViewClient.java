package io.extremus.kittuov.tapquick.utils;

import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by kittuov on 29/4/16.
 */
public class CallbackWebViewClient extends WebViewClient {
    PageErrorListener mListener;


    public CallbackWebViewClient(PageErrorListener listner){
        mListener = listner;
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        mListener.onReceivedHttpError(error);
    }

    public void setPageLoadListener(PageErrorListener listener){
        mListener = listener;
    }
}
