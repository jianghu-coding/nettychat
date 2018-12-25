package com.im.nettychat.cache;

import com.im.nettychat.config.ServerConfig;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
public class RedisBootstrap {

    protected static volatile JedisPool jedisPool = null;

    public synchronized static void init() {
        initialJedisPool();
    }

    private synchronized static void initialJedisPool() {
        if (jedisPool != null) {
            return;
        }
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(ServerConfig.getRedisMaxActive());
        config.setMaxIdle(ServerConfig.getRedisMaxIdle());
        config.setMaxWaitMillis(ServerConfig.getRedisMaxWaitMillis());
        // 使用时进行扫描，确保都可用
        config.setTestOnBorrow(ServerConfig.getRedisTestOnBorrow());
        config.setTestWhileIdle(true);
        // 还回线程池时进行扫描
        config.setTestOnReturn(ServerConfig.getRedisTestOnReturn());
        jedisPool = new JedisPool(config, ServerConfig.getRedisHost(), ServerConfig.getRedisPort(), ServerConfig.getRedisTimeOut());
    }
}
