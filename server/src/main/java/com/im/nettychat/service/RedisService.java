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

    public <T> T vGetObject(CacheName cacheName, String key, Class<T> cassClass) {
        if (cacheName.getType() != CacheType.V) {
            throw new IllegalArgumentException("expected V found " + cacheName.getType());
        }
        byte[] bytes = getJedis().get(cacheName.getPrefix().concat(key).getBytes());
        if (bytes != null && bytes.length > 0) {
            return Serializer.DEFAULT.deserialize(cassClass, bytes);
        }
        return null;
    }

    public Boolean sExist(CacheName cacheName, String username) {
        if (cacheName.getType() != CacheType.S) {
            throw new IllegalArgumentException("expected S found " + cacheName.getType());
        }
        return getJedis().sismember(cacheName.name(), username);
    }


    public void hSet(CacheName cacheName, String username, String val) {
        if (cacheName.getType() != CacheType.H) {
            throw new IllegalArgumentException("expected V found " + cacheName.getType());
        }
        getJedis().hset(cacheName.name(), username, val);
    }


    public long hDel(CacheName cacheName) {
        if (cacheName.getType() != CacheType.V) {
            throw new IllegalArgumentException("expected V found " + cacheName.getType());
        }
        return getJedis().del(cacheName.name());
    }

    public String hGet(CacheName cacheName, String username) {
        if (cacheName.getType() != CacheType.H) {
            throw new IllegalArgumentException("expected H found " + cacheName.getType());
        }
        return getJedis().hget(cacheName.name(), username);
    }

    public Object vGetString(CacheName cacheName, String key) {
        if (cacheName.getType() != CacheType.V) {
            throw new IllegalArgumentException("expected V found " + cacheName.getType());
        }
        return getJedis().get(cacheName.getPrefix().concat(key));
    }

    public Long vIncr(CacheName cacheName) {
        if (cacheName.getType() != CacheType.V) {
            throw new IllegalArgumentException("expected V found " + cacheName.getType());
        }
        return getJedis().incr(cacheName.name());
    }

    public void vSetObject(CacheName cacheName, String key, Object obj) {
        if (cacheName.getType() != CacheType.V) {
            throw new IllegalArgumentException("expected V found " + cacheName.getType());
        }
        if (cacheName.getExpiration() > 0) {
            getJedis().psetex(cacheName.getPrefix().concat(key).getBytes(), cacheName.getExpiration() * 1000, Serializer.DEFAULT.serialize(obj));
        } else {
            getJedis().set(cacheName.getPrefix().concat(key).getBytes(), Serializer.DEFAULT.serialize(obj));
        }
    }

    public void vSetString(CacheName cacheName, String key, String val) {
        if (cacheName.getType() != CacheType.V) {
            throw new IllegalArgumentException("expected V found " + cacheName.getType());
        }
        if (cacheName.getExpiration() > 0) {
            getJedis().psetex(cacheName.getPrefix().concat(key), cacheName.getExpiration(), val);
        } else {
            getJedis().set(cacheName.getPrefix().concat(key), val);
        }
    }

    public boolean vSetNx(CacheName cacheName, String val) {
        if (cacheName.getType() != CacheType.V) {
            throw new IllegalArgumentException("expected V found " + cacheName.getType());
        }
        long success = getJedis().setnx(cacheName.name(), val);
        if (cacheName.getExpiration() > 0 && success > 0) {
            getJedis().expire(cacheName.name(), (int) cacheName.getExpiration());
        }
        return success > 0;
    }

    private Jedis getJedis() {
        return RedisPool.getJedis();
    }
}
