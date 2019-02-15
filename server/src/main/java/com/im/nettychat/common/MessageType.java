package com.im.nettychat.common;

/**
 * @author hejianglong
 * @date 2019/2/15.
 */
public enum MessageType {

    TEXT(1), IMAGE(2);

    int code;

    MessageType(int code) {
        this.code = code;
    }

    public static MessageType parse(int code) {
        if (code == 1) {
            return TEXT;
        } else if (code == 2) {
            return IMAGE;
        }
        return null;
    }

    public int getCode() {
        return code;
    }
}
