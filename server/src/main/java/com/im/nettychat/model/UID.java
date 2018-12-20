/*
 * Project: com.im.nettychat.model
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
package com.im.nettychat.model;

import lombok.Data;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/20 下午8:49
 */
@Data
public class UID {

    private String username;

    private Long userId;
}
