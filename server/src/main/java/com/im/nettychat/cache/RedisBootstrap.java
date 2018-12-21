package com.im.nettychat.cache;

import com.im.nettychat.config.ServerConfig;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

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

    private static RedisConnectionFactory getRedisConnectionFactory() {
        JedisShardInfo jedisShardInfo = new JedisShardInfo(ServerConfig.getRedisHost(), ServerConfig.getPort());
        RedisConnectionFactory connectionFactory = new JedisConnectionFactory(jedisShardInfo);
        return connectionFactory;
    }
}
