/*
 * Project: com.im.nettychat.protocol.request.group
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
package com.chat.androidclient.mvvm.procotol.request;

import com.chat.androidclient.mvvm.model.Command;
import com.chat.androidclient.mvvm.procotol.Packet;


/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/23 下午1:17
 */

public class JoinGroupRequest extends Packet {

    private Long groupId;

    @Override
    public byte getCommand() {
        return Command.JOIN_GROUP;
    }

    public JoinGroupRequest(Long mGroupId) {
        groupId = mGroupId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public JoinGroupRequest setGroupId(Long mGroupId) {
        groupId = mGroupId;
        return this;
    }
}
