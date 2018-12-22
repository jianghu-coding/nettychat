package com.im.nettychat.common;

import io.netty.util.AttributeKey;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/22 下午3:34
 */
public interface AttributeKeys {
    AttributeKey<Session> SESSION_ATTRIBUTE_KEY = AttributeKey.newInstance("session");
}
