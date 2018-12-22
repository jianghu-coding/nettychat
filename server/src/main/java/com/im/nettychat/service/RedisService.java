package com.im.nettychat.service;

import com.im.nettychat.cache.CacheName;
import com.im.nettychat.cache.CacheType;
import com.im.nettychat.cache.RedisPool;
import com.im.nettychat.proxy.CglibJedisInterceptor;
import com.im.nettychat.serialize.Serializer;
import redis.clients.jedis.Jedis;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
public class RedisService {

    public static final RedisService redisService = (RedisService) CglibJedisInterceptor.getCglibProxy(RedisService.class);

    public Object vGet(CacheName cacheName, String key) {
        if (cacheName.getType() != CacheType.V) {
            throw new IllegalArgumentException("expected V found " + cacheName.getType());
        }
        return getJedis().get(cacheName.getPrefix().concat(key));
    }

    public Boolean sExist(CacheName cacheName, String username) {
        if (cacheName.getType() != CacheType.S) {
            throw new IllegalArgumentException("expected V found " + cacheName.getType());
        }
        return getJedis().sismember(cacheName.name(), username);
    }

    public Object hGet(CacheName cacheName, String username) {
        if (cacheName.getType() != CacheType.H) {
            throw new IllegalArgumentException("expected V found " + cacheName.getType());
        }
        return getJedis().hget(cacheName.name(), username);
    }

    public Long vIncr(CacheName cacheName) {
        if (cacheName.getType() != CacheType.V) {
            throw new IllegalArgumentException("expected V found " + cacheName.getType());
        }
        return getJedis().incr(cacheName.name());
    }

    public void vSet(CacheName cacheName, String key, Object obj) {
        if (cacheName.getType() != CacheType.V) {
            throw new IllegalArgumentException("expected V found " + cacheName.getType());
        }
        if (cacheName.getExpiration() > 0) {
            getJedis().psetex(cacheName.getPrefix().concat(key).getBytes(), cacheName.getExpiration() * 1000, Serializer.DEFAULT.serialize(obj));
        } else {
            getJedis().set(cacheName.getPrefix().concat(key).getBytes(), Serializer.DEFAULT.serialize(obj));
        }
    }

    public void hSet(CacheName cacheName, String username, String val) {
        if (cacheName.getType() != CacheType.H) {
            throw new IllegalArgumentException("expected V found " + cacheName.getType());
        }
        getJedis().hset(cacheName.name(), username, val);
    }

    public boolean vSetNx(CacheName cacheName, String val) {
        if (cacheName.getType() != CacheType.V) {
            throw new IllegalArgumentException("expected V found " + cacheName.getType());
        }
        System.out.println(cacheName.name() + "--" + val);
        long success = getJedis().setnx(cacheName.name(), val);
        System.out.println("success? :" + success);
        if (cacheName.getExpiration() > 0 && success > 0) {
            getJedis().expire(cacheName.name(), (int) cacheName.getExpiration());
        }
        return success > 0;
    }

    public long hDel(CacheName cacheName) {
        if (cacheName.getType() != CacheType.V) {
            throw new IllegalArgumentException("expected V found " + cacheName.getType());
        }
        return getJedis().del(cacheName.name());
    }

    private Jedis getJedis() {
        return RedisPool.getJedis();
    }
}
