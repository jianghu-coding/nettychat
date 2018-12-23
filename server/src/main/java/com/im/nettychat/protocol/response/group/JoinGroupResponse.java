/*
 * Project: com.im.nettychat.protocol.response.group
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
package com.im.nettychat.protocol.response.group;

import com.im.nettychat.common.Command;
import lombok.Data;
import java.util.List;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/23 下午1:20
 */
@Data
public class JoinGroupResponse extends UserGroupResponse {

    private Long groupId;

    private String groupName;

    private String icon;

    private List<Long> userIds;

    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_RESPONSE;
    }
}
