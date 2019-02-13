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
 * @date 2018/12/23 下午2:13
 */

public class GetUserGroupResponse extends PacketResponse {


    private String groupName;

    private Long groupId;

    private List<Long> userIds;

    private String icon;

    private Long owner;

    private String desc;

    @Override
    public byte getCommand() {
        return Command.GET_USER_GROUP_RESPONSE;
    }


    public String getGroupName() {
        return groupName;
    }

    public GetUserGroupResponse setGroupName(String mGroupName) {
        groupName = mGroupName;
        return this;
    }

    public Long getGroupId() {
        return groupId;
    }

    public GetUserGroupResponse setGroupId(Long mGroupId) {
        groupId = mGroupId;
        return this;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public GetUserGroupResponse setUserIds(List<Long> mUserIds) {
        userIds = mUserIds;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public GetUserGroupResponse setIcon(String mIcon) {
        icon = mIcon;
        return this;
    }

    public Long getOwner() {
        return owner;
    }

    public GetUserGroupResponse setOwner(Long mOwner) {
        owner = mOwner;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public GetUserGroupResponse setDesc(String mDesc) {
        desc = mDesc;
        return this;
    }
}
