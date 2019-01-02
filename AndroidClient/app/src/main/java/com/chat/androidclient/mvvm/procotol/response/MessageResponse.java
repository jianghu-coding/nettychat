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
package com.chat.androidclient.mvvm.procotol.response;

import com.chat.androidclient.mvvm.model.Command;
import com.chat.androidclient.mvvm.model.PacketResponse;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/22 下午6:16
 */
@Entity
public class MessageResponse extends PacketResponse {
    @Id
    private Long id;
    private Long fromUserId;
    private Long toUserId;
    private Long time;
    private String message;

    public Long getTime() {
        return time;
    }

    public MessageResponse setTime(Long mTime) {
        time = mTime;
        return this;
    }

    @Generated(hash = 1554578866)
    public MessageResponse(Long id, Long fromUserId, Long toUserId, Long time,
            String message) {
        this.id = id;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.time = time;
        this.message = message;
    }

    @Generated(hash = 2003436558)
    public MessageResponse() {
    }


    @Override
    public byte getCommand() {
        return Command.SEND_MESSAGE_RESPONSE;
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public Long getFromUserId() {
        return this.fromUserId;
    }


    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }


    public String getMessage() {
        return this.message;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public MessageResponse setToUserId(Long mToUserId) {
        toUserId = mToUserId;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
