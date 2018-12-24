package com.im.nettychat.cache;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
public enum CacheName {

    REGISTER_LOCK(CacheType.V, 5),

    USER_INFO(CacheType.V, 0),

    USER_ID(CacheType.V, 0),

    USERNAME_ID(CacheType.H, 0),

    USER_GROUP(CacheType.H, 0),

    USER_GROUP_ID(CacheType.V, 0),

    USER_ID_USER_GROUP(CacheType.S, 0),

    USER_FRIEND(CacheType.S, 0),

    OFFLINE_MESSAGE(CacheType.L, 0);

    /**
     * 缓存类型
     */
    private CacheType type;

    /**
     * 过期时间
     */
    private long expiration;

    /**
     * 缓存前缀
     */
    private String prefix;

    CacheName(CacheType cacheType, long expiration) {
        this.type = cacheType;
        this.expiration = expiration;
        this.prefix = this.name().concat(":");
    }

    public CacheType getType() {
        return type;
    }

    public long getExpiration() {
        return expiration;
    }

    public String getPrefix() {
        return prefix;
    }
}
