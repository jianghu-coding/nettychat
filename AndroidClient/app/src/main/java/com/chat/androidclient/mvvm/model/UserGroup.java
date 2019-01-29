/*
 * Project: com.im.nettychat.model
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
package com.chat.androidclient.mvvm.model;

import java.util.List;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/23 下午3:41
 */

public class UserGroup {

    private Long groupId;

    private Long owner;

    private List<Long> userIds;

    private String userIdsStr;

    private String groupName;

    private String icon;

    private String desc;

    public Long getGroupId() {
        return groupId;
    }

    public UserGroup setGroupId(Long mGroupId) {
        groupId = mGroupId;
        return this;
    }

    public Long getOwner() {
        return owner;
    }

    public UserGroup setOwner(Long mOwner) {
        owner = mOwner;
        return this;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public UserGroup setUserIds(List<Long> mUserIds) {
        userIds = mUserIds;
        return this;
    }

    public String getUserIdsStr() {
        return userIdsStr;
    }

    public UserGroup setUserIdsStr(String mUserIdsStr) {
        userIdsStr = mUserIdsStr;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public UserGroup setGroupName(String mGroupName) {
        groupName = mGroupName;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public UserGroup setIcon(String mIcon) {
        icon = mIcon;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public UserGroup setDesc(String mDesc) {
        desc = mDesc;
        return this;
    }

    @Override
    public String toString() {
        return "UserGroup{" +
                "groupId=" + groupId +
                ", owner=" + owner +
                ", userIds=" + userIds +
                ", userIdsStr='" + userIdsStr + '\'' +
                ", groupName='" + groupName + '\'' +
                ", icon='" + icon + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
