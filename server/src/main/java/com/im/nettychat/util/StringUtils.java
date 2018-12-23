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

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/22 上午12:33
 */
public class StringUtils {

    public static boolean isEmpty(String val) {
        return val == null || val.trim().length() == 0;
    }

    public static boolean isNotEmpty(String val) {
        return val != null && val.trim().length() > 0;
    }
}
