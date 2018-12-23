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
package com.im.nettychat.protocol.response;

import com.im.nettychat.common.Command;
import com.im.nettychat.protocol.ResponsePacket;
import lombok.Data;
import java.util.List;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/22 下午10:26
 */
@Data
public class CreateGroupResponse extends ResponsePacket {

    private String groupName;

    private Long groupId;

    private List<Long> userIds;

    private String icon;

    private String desc;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
