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
import redis.clients.jedis.JedisShardInfo;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
public class RedisBootstrap {

    static {
        initRedisTemplate();
    }

    private static RedisTemplate<String, Object> REDIS_TEMPLATE;

    private static void initRedisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setConnectionFactory(getRedisConnectionFactory());
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        REDIS_TEMPLATE = redisTemplate;
    }

    private static RedisConnectionFactory getRedisConnectionFactory() {
        JedisShardInfo jedisShardInfo = new JedisShardInfo(ServerConfig.getRedisHost(), ServerConfig.getPort());
        RedisConnectionFactory connectionFactory = new JedisConnectionFactory(jedisShardInfo);
        return connectionFactory;
    }

    public static ValueOperations<String, Object> getValueOps() {
        return REDIS_TEMPLATE.opsForValue();
    }

    public static ListOperations<String, Object> getListOps() {
        return REDIS_TEMPLATE.opsForList();
    }

    public static SetOperations<String, Object> getSetOps() {
        return REDIS_TEMPLATE.opsForSet();
    }

    public static ZSetOperations<String, Object> getZSetOps() {
        return REDIS_TEMPLATE.opsForZSet();
    }

    public static HashOperations<String, String, Object> getHashOps() {
        return REDIS_TEMPLATE.opsForHash();
    }
}
