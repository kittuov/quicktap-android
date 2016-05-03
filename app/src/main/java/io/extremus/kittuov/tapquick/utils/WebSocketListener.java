package io.extremus.kittuov.tapquick.utils;

/**
 * Created by kittuov on 1/5/16.
 */
public interface WebSocketListener {
    void OnMessage(String data);
    void OnDisconnect();
}
