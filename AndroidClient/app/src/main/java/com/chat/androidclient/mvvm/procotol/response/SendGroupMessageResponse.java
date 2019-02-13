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
package com.chat.androidclient.mvvm.procotol.response;

import com.chat.androidclient.mvvm.model.Command;
import com.chat.androidclient.mvvm.model.PacketResponse;


/**
 * 收到群消息
 */

public class SendGroupMessageResponse extends PacketResponse {

    private Long sendUserId;

    private String message;

    @Override
    public byte getCommand() {
        return Command.SEND_GROUP_MESSAGE_RESPONSE;
    }

    public Long getSendUserId() {
        return sendUserId;
    }

    public SendGroupMessageResponse setSendUserId(Long mSendUserId) {
        sendUserId = mSendUserId;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public SendGroupMessageResponse setMessage(String mMessage) {
        message = mMessage;
        return this;
    }
}
