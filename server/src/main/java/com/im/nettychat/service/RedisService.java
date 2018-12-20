package com.im.nettychat.service;

import com.im.nettychat.cache.CacheName;
import com.im.nettychat.cache.CacheType;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import static com.im.nettychat.cache.RedisBootstrap.*;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
public class RedisService {

    public static final RedisService redisService = new RedisService();

    private static final ValueOperations<String, Object> valueOps = getValueOps();

    private static final ListOperations<String, Object> listOps = getListOps();

    private static final SetOperations<String, Object> setOps = getSetOps();

    private static final ZSetOperations<String, Object> zSetOps = getZSetOps();

    private static final HashOperations<String, String, Object> hashOps = getHashOps();

    public Object vGet(CacheName cacheName, String key) {
        if (cacheName.getType() != CacheType.V) {
            throw new IllegalArgumentException("expected V found " + cacheName.getType());
        }
        return valueOps.get(cacheName.getPrefix().concat(key));
    }

    public Boolean sExist(CacheName cacheName, String username) {
        if (cacheName.getType() != CacheType.S) {
            throw new IllegalArgumentException("expected V found " + cacheName.getType());
        }
        return setOps.isMember(cacheName.name(), username);
    }

    public Object hGet(CacheName cacheName, String username) {
        if (cacheName.getType() != CacheType.S) {
            throw new IllegalArgumentException("expected V found " + cacheName.getType());
        }
        return hashOps.get(cacheName.name(), username);
    }
}
