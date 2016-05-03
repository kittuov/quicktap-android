package io.extremus.kittuov.tapquick.utils;

import android.webkit.WebResourceError;

/**
 * Created by kittuov on 29/4/16.
 */
public interface PageErrorListener {
    public void onReceivedHttpError(WebResourceError errorResponse);
}
