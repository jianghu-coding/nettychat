package com.im.nettychat.cache;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
public enum CacheType {
    /**
     * String（字符串），仅该类型可以使用cacheManager和@Cacheable处理，并且expiration为0时候，表示不过期
     */
    V,

    /**
     * List（列表）
     */
    L,

    /**
     * Set（集合）
     */
    S,

    /**
     * SortedSet（有序集合）
     */
    Z,

    /**
     * Hash（哈希表）
     */
    H
}
