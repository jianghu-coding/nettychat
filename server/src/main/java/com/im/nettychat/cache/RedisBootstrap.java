package com.im.nettychat.cache;

import com.im.nettychat.config.ServerConfig;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
public class RedisBootstrap {

    private static int MAX_ACTIVE = 500;

    private static int MAX_IDLE = 100;

    private static int MAX_WAIT = 10 * 1000;

    private static int TIMEOUT = 10 * 1000;

    private static boolean TEST_ON_BORROW = true;

    protected static volatile JedisPool jedisPool = null;

    public synchronized static void init() {
        initialJedisPool();
    }

    private synchronized static void initialJedisPool() {
        if (jedisPool != null) {
            return;
        }
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(MAX_ACTIVE);
        config.setMaxIdle(MAX_IDLE);
        config.setMaxWaitMillis(MAX_WAIT);
        // 使用时进行扫描，确保都可用
        config.setTestOnBorrow(TEST_ON_BORROW);
        config.setTestWhileIdle(true);
        // 还回线程池时进行扫描
        config.setTestOnReturn(true);
        jedisPool = new JedisPool(config, ServerConfig.getRedisHost(), ServerConfig.getRedisPort(), TIMEOUT);
    }
}
