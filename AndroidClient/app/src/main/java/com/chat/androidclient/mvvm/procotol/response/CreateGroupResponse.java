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
import com.chat.androidclient.mvvm.procotol.Packet;

import java.util.List;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/22 下午10:26
 */

public class CreateGroupResponse extends PacketResponse {

    private String groupName;

    private Long groupId;

    private List<Long> userIds;

    private String icon;

    @Override
    public byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }

    public String getGroupName() {
        return groupName;
    }

    public CreateGroupResponse setGroupName(String mGroupName) {
        groupName = mGroupName;
        return this;
    }

    public Long getGroupId() {
        return groupId;
    }

    public CreateGroupResponse setGroupId(Long mGroupId) {
        groupId = mGroupId;
        return this;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public CreateGroupResponse setUserIds(List<Long> mUserIds) {
        userIds = mUserIds;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public CreateGroupResponse setIcon(String mIcon) {
        icon = mIcon;
        return this;
    }
}
