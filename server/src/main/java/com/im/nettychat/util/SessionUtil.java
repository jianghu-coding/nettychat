/*
 * Project: com.im.nettychat.util
 * 
 * File Created at 2018/12/22
 * 
 * Copyright 2018 CMCC Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * ZYHY Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license.
 */
package com.im.nettychat.util;

import com.im.nettychat.common.Session;
import io.netty.channel.Channel;
import java.util.concurrent.ConcurrentHashMap;
import static com.im.nettychat.common.AttributeKeys.SESSION_ATTRIBUTE_KEY;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/22 下午3:07
 */
public class SessionUtil {

    private final static ConcurrentHashMap<Long, Channel> USER_ID_CHANNEL = new ConcurrentHashMap<Long, Channel>();

    public static Channel getChannel(Long userId) {
        return USER_ID_CHANNEL.get(userId);
    }

    public static Session getSession(Channel channel) {
        return channel.attr(SESSION_ATTRIBUTE_KEY).get();
    }

    public static void bindSession(Session session, Channel channel) {
        channel.attr(SESSION_ATTRIBUTE_KEY).set(session);
        USER_ID_CHANNEL.put(session.getUserId(), channel);
    }

    public static boolean notLogin(Channel channel) {
        return getSession(channel) == null;
    }

    public static void unBindSession(Channel channel) {
        Session session = channel.attr(SESSION_ATTRIBUTE_KEY).get();
        USER_ID_CHANNEL.remove(session.getUserId());
    }

    public static boolean hasLogin(Channel channel) {
        return getSession(channel) != null;
    }
}
