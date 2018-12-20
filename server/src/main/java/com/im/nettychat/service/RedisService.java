package com.im.nettychat.service;

import com.im.nettychat.cache.CacheName;
import com.im.nettychat.cache.CacheType;
import com.im.nettychat.serialize.Serializer;
import static com.im.nettychat.cache.RedisBootstrap.*;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
public class RedisService {

    public static final RedisService redisService = new RedisService();

    public Object vGet(CacheName cacheName, String key) {
        if (cacheName.getType() != CacheType.V) {
            throw new IllegalArgumentException("expected V found " + cacheName.getType());
        }
        return jedis.get(cacheName.getPrefix().concat(key));
    }

    public Boolean sExist(CacheName cacheName, String username) {
        if (cacheName.getType() != CacheType.S) {
            throw new IllegalArgumentException("expected V found " + cacheName.getType());
        }
        return jedis.sismember(cacheName.name(), username);
    }

    public Object hGet(CacheName cacheName, String username) {
        if (cacheName.getType() != CacheType.H) {
            throw new IllegalArgumentException("expected V found " + cacheName.getType());
        }
        return jedis.hget(cacheName.name(), username);
    }

    public Long vIncr(CacheName cacheName) {
        if (cacheName.getType() != CacheType.V) {
            throw new IllegalArgumentException("expected V found " + cacheName.getType());
        }
        return jedis.incr(cacheName.name());
    }

    public void vSet(CacheName cacheName, String key, Object obj) {
        if (cacheName.getType() != CacheType.V) {
            throw new IllegalArgumentException("expected V found " + cacheName.getType());
        }
        jedis.set(cacheName.getPrefix().concat(key).getBytes(), Serializer.DEFAULT.serialize(obj));
    }
}
