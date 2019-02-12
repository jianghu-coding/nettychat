package com.im.nettychat.common;

/**
 * @author hejianglong
 * @date 2019/1/30.
 */
public enum  ResourceType {

    /**
     * 表情
     */
    EMOTICON(1);

    int code;

    ResourceType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
