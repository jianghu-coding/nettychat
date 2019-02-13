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
 * @date 2018/12/23 下午2:11
 */

public class GetUserGroupRequest extends Packet {

    private Long groupId;

    public GetUserGroupRequest(Long mGroupId) {
        groupId = mGroupId;
    }

    @Override
    public byte getCommand() {
        return Command.GET_USER_GROUP;
    }

    public Long getGroupId() {
        return groupId;
    }

    public GetUserGroupRequest setGroupId(Long mGroupId) {
        groupId = mGroupId;
        return this;
    }
}
