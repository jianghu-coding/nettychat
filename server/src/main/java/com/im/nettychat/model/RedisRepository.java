package com.im.nettychat.model;

import com.im.nettychat.cache.CacheName;
import com.im.nettychat.cache.CacheType;
import com.im.nettychat.cache.RedisPool;
import com.im.nettychat.proxy.CglibJedisInterceptor;
import com.im.nettychat.serialize.Serializer;
import redis.clients.jedis.Jedis;
import java.util.Map;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
public class RedisRepository {

    public static final RedisRepository redisRepository = (RedisRepository) CglibJedisInterceptor.getCglibProxy(RedisRepository.class);

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

    public boolean sExist(CacheName cacheName, String key) {
        if (cacheName.getType() != CacheType.S) {
            throw new IllegalArgumentException("expected S found " + cacheName.getType());
        }
        return getJedis().sismember(cacheName.name(), key);
    }

    public void hSet(CacheName cacheName, String key, String val) {
        if (cacheName.getType() != CacheType.H) {
            throw new IllegalArgumentException("expected H found " + cacheName.getType());
        }
        getJedis().hset(cacheName.name(), key, val);
    }

    public void hSet(CacheName cacheName, String key, String field, String val) {
        if (cacheName.getType() != CacheType.H) {
            throw new IllegalArgumentException("expected H found " + cacheName.getType());
        }
        getJedis().hset(cacheName.getPrefix().concat(key), field, val);
    }

    public void hMSet(CacheName cacheName, String key, Map<String, String> hash) {
        if (cacheName.getType() != CacheType.H) {
            throw new IllegalArgumentException("expected H found " + cacheName.getType());
        }
        getJedis().hmset(cacheName.getPrefix().concat(key), hash);
    }

    public long hDel(CacheName cacheName) {
        if (cacheName.getType() != CacheType.V) {
            throw new IllegalArgumentException("expected H found " + cacheName.getType());
        }
        return getJedis().del(cacheName.name());
    }

    public String hGet(CacheName cacheName, String key) {
        if (cacheName.getType() != CacheType.H) {
            throw new IllegalArgumentException("expected H found " + cacheName.getType());
        }
        return getJedis().hget(cacheName.name(), key);
    }

    public boolean hExits(CacheName cacheName, String key) {
        return getJedis().hexists(cacheName.name(), key);
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

    public Boolean vExits(CacheName cacheName, String key) {
        if (cacheName.getType() != CacheType.V) {
            throw new IllegalArgumentException("expected V found " + cacheName.getType());
        }
        return getJedis().exists(cacheName.getPrefix().concat(key));
    }

    private Jedis getJedis() {
        return RedisPool.getJedis();
    }
}
