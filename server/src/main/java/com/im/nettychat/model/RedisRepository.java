package com.im.nettychat.model;

import com.im.nettychat.cache.CacheName;
import com.im.nettychat.cache.CacheType;
import com.im.nettychat.cache.RedisPool;
import com.im.nettychat.proxy.CglibJedisInterceptor;
import com.im.nettychat.serialize.Serializer;
import redis.clients.jedis.Jedis;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public boolean sExist(CacheName cacheName, String field, String val) {
        if (cacheName.getType() != CacheType.S) {
            throw new IllegalArgumentException("expected S found " + cacheName.getType());
        }
        return getJedis().sismember(cacheName.getPrefix().concat(field), val);
    }

    public void hSet(CacheName cacheName, String field, String val) {
        if (cacheName.getType() != CacheType.H) {
            throw new IllegalArgumentException("expected H found " + cacheName.getType());
        }
        getJedis().hset(cacheName.name(), field, val);
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

    public List<String> hMGet(CacheName cacheName, String key, String... fields) {
        if (cacheName.getType() != CacheType.H) {
            throw new IllegalArgumentException("expected H found " + cacheName.getType());
        }
        return getJedis().hmget(cacheName.getPrefix().concat(key), fields);
    }

    public long hDel(CacheName cacheName) {
        if (cacheName.getType() != CacheType.V) {
            throw new IllegalArgumentException("expected H found " + cacheName.getType());
        }
        return getJedis().del(cacheName.name());
    }

    public String hGet(CacheName cacheName, String field) {
        if (cacheName.getType() != CacheType.H) {
            throw new IllegalArgumentException("expected H found " + cacheName.getType());
        }
        return getJedis().hget(cacheName.name(), field);
    }

    public boolean hExits(CacheName cacheName, String field) {
        return getJedis().hexists(cacheName.name(), field);
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

    public boolean keyExits(CacheName cacheName, String key) {
        return getJedis().exists(cacheName.getPrefix().concat(key));
    }

    public void lPush(CacheName cacheName, String key, String...val) {
        if (cacheName.getType() != CacheType.L) {
            throw new IllegalArgumentException("expected L found " + cacheName.getType());
        }
        getJedis().lpush(cacheName.getPrefix().concat(key), val);
    }

    public void lPush(CacheName cacheName, String key, Object obj) {
        if (cacheName.getType() != CacheType.L) {
            throw new IllegalArgumentException("expected L found " + cacheName.getType());
        }
        getJedis().lpush(cacheName.getPrefix().concat(key).getBytes(), Serializer.DEFAULT.serialize(obj));
    }

    public List<String> lRange(CacheName cacheName, String key, int start, int end) {
        if (cacheName.getType() != CacheType.L) {
            throw new IllegalArgumentException("expected L found " + cacheName.getType());
        }
        return getJedis().lrange(cacheName.getPrefix().concat(key), start, end);
    }

    public Long removeKey(CacheName cacheName, String key) {
        if (cacheName.getType() != CacheType.L) {
            throw new IllegalArgumentException("expected L found " + cacheName.getType());
        }
        return getJedis().del(cacheName.getPrefix().concat(key));
    }

    public <T> List<T> lRangeObject(CacheName cacheName, String key, long start, long end, Class<T> cassClass) {
        if (cacheName.getType() != CacheType.L) {
            throw new IllegalArgumentException("expected L found " + cacheName.getType());
        }
        List<T> objs = new ArrayList<>();
        List<byte[]> rs = getJedis().lrange(cacheName.getPrefix().concat(key).getBytes(), start, end);
        for(byte[] bytes: rs) {
            objs.add(Serializer.DEFAULT.deserialize(cassClass, bytes));
        }
        return objs;
    }

    public void sAdd(CacheName cacheName, String key, String...val) {
        if (cacheName.getType() != CacheType.S) {
            throw new IllegalArgumentException("expected S found " + cacheName.getType());
        }
        getJedis().sadd(cacheName.getPrefix().concat(key), val);
    }

    public Set<String> sGet(CacheName cacheName, String key) {
        if (cacheName.getType() != CacheType.S) {
            throw new IllegalArgumentException("expected S found " + cacheName.getType());
        }
        return getJedis().smembers(cacheName.getPrefix().concat(key));
    }

    public void sRemove(CacheName cacheName, String key, String... members) {
        if (cacheName.getType() != CacheType.S) {
            throw new IllegalArgumentException("expected S found " + cacheName.getType());
        }
        getJedis().srem(cacheName.getPrefix().concat(key), members);
    }

    public boolean sismember(CacheName cacheName, String key, String val){
        return getJedis().sismember(cacheName.getPrefix().concat(key), val);
    }

    private Jedis getJedis() {
        return RedisPool.getJedis();
    }
}
