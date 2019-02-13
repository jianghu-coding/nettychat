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

import java.util.List;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/23 下午1:20
 */

public class JoinGroupResponse extends PacketResponse {

    private Long groupId;

    private String groupName;

    private String icon;

    private List<Long> userIds;

    @Override
    public byte getCommand() {
        return Command.JOIN_GROUP_RESPONSE;
    }

    public Long getGroupId() {
        return groupId;
    }

    public JoinGroupResponse setGroupId(Long mGroupId) {
        groupId = mGroupId;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public JoinGroupResponse setGroupName(String mGroupName) {
        groupName = mGroupName;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public JoinGroupResponse setIcon(String mIcon) {
        icon = mIcon;
        return this;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public JoinGroupResponse setUserIds(List<Long> mUserIds) {
        userIds = mUserIds;
        return this;
    }
}
