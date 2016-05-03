package io.extremus.kittuov.tapquick.utils;

import org.json.JSONException;

/**
 * Created by kittuov on 29/4/16.
 */
public interface PageLoadListner {
    public void onPageFinished(String url, int status, String response);
}
