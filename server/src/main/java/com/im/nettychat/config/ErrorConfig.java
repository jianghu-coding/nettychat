/*
 * Project: com.im.nettychat.config
 * 
 * File Created at 2018/12/20
 * 
 * Copyright 2018 CMCC Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * ZYHY Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license.
 */
package com.im.nettychat.config;

import static com.im.nettychat.config.load.ConfigProperties.ERROR_PROPERTIES;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/20 下午9:21
 */
public class ErrorConfig {

    public static String getError(String key) {
        return ERROR_PROPERTIES.getProperty(key, "unknown error");
    }
}
