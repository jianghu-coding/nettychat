package com.im.nettychat.cache;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
public enum CacheName {

    USER_INFO(CacheType.V, 0),

    USER_ID(CacheType.V, 0),

    USERNAME_ID(CacheType.H, 0);

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
