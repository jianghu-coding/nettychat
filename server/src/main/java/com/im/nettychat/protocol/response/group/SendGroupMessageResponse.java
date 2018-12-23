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
import com.im.nettychat.protocol.PacketResponse;
import lombok.Data;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/23 下午6:24
 */
@Data
public class SendGroupMessageResponse extends PacketResponse {

    private Long sendUserId;

    private String message;

    @Override
    public Byte getCommand() {
        return Command.SEND_GROUP_MESSAGE_RESPONSE;
    }
}
