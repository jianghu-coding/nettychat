/*
 * Project: com.im.nettychat.util
 * 
 * File Created at 2018/12/22
 * 
 * Copyright 2018 CMCC Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * ZYHY Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license.
 */
package com.im.nettychat.util;

import com.im.nettychat.cache.CacheName;
import static com.im.nettychat.model.RedisRepository.redisRepository;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/22 上午11:00
 */
public class LockUtil {

    private static final String LOCK_VAL = "1";

    /**
     * CacheName需要指定过期时间不然此处将是无限循环
     * @param key
     */
    public static void lock(CacheName key) {
        boolean success = false;
        for(;;) {
            success = redisRepository.vSetNx(key, LOCK_VAL);
            if (success) {
                return;
            }
        }
    }

    public static void unLock(CacheName key) {
        redisRepository.hDel(key);
    }
}
