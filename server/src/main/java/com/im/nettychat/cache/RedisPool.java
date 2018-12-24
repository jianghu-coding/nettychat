/*
 * Project: com.im.nettychat.cache
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
package com.im.nettychat.cache;

import redis.clients.jedis.Jedis;
import static com.im.nettychat.cache.LocalRSession.LOCAL_JEDIS;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/22 下午12:07
 */
public class RedisPool extends RedisBootstrap {

    public static Jedis getJedis() {
        Jedis jedis = LOCAL_JEDIS.get();
        if (jedis != null) {
            return jedis;
        }
        return jedisPool.getResource();
    }
}
