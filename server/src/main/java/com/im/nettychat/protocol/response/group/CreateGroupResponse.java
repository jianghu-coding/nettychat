/*
 * Project: com.im.nettychat.protocol.response
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
package com.im.nettychat.protocol.response.group;

import com.im.nettychat.common.Command;
import lombok.Data;
import java.util.List;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/22 下午10:26
 */
@Data
public class CreateGroupResponse extends UserGroupResponse {

    private String groupName;

    private Long groupId;

    private List<Long> userIds;

    private String icon;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
