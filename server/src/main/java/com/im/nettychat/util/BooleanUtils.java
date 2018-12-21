/*
 * Project: com.im.nettychat.util
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
package com.im.nettychat.util;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/20 下午8:41
 */
public class BooleanUtils {

    public static boolean isTrue(Boolean val) {
        return val != null && val.equals(true);
    }
}
