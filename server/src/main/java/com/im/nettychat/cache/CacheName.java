package com.im.nettychat.cache;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
public enum CacheName {

    USER_INFO(CacheType.V, 0);

    /**
     * 缓存类型
     */
    private CacheType type;

    /**
     * 缓存前缀
     */
    private String prefix;

    /**
     * 缓存匹配器
     */
    private String regex;

    /**
     * 过期时间
     */
    private long expiration;

    CacheName(CacheType cacheType, long expiration) {
        this.type = cacheType;
        this.expiration = expiration;
        this.prefix = this.name().concat(":");
        this.regex = prefix.concat("*");
    }

    public CacheType getType() {
        return type;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getRegex() {
        return regex;
    }

    public long getExpiration() {
        return expiration;
    }
}
