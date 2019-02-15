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
import com.im.nettychat.protocol.PacketResponse;
import lombok.Data;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/22 下午6:16
 */
@Data
public class MessageResponse extends PacketResponse {

    private Long fromUserId;

    private String message;

    private int type;

    @Override
    public Byte getCommand() {
        return Command.SEND_MESSAGE_RESPONSE;
    }
}
