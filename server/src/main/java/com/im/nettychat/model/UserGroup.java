/*
 * Project: com.im.nettychat.model
 * 
 * File Created at 2018/12/23
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

import java.util.List;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/23 下午3:41
 */
@Data
public class UserGroup {

    private Long groupId;

    private Long owner;

    private List<Long> userIds;

    private String userIdsStr;

    private String groupName;

    private String icon;

    private String desc;

}
