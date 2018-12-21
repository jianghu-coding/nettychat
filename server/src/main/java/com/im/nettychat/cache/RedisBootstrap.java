package com.im.nettychat.cache;

import com.im.nettychat.config.ServerConfig;
import redis.clients.jedis.Jedis;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
public class RedisBootstrap {

    public static Jedis jedis;

    public static void init() {
        initJedis();
    }

    private static void initJedis() {
        jedis = new Jedis(ServerConfig.getRedisHost(), ServerConfig.getRedisPort());
    }
}
