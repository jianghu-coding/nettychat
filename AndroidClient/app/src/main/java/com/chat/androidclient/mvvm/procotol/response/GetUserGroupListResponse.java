package com.chat.androidclient.mvvm.procotol.response;

import com.chat.androidclient.mvvm.model.Command;
import com.chat.androidclient.mvvm.model.PacketResponse;
import com.chat.androidclient.mvvm.model.UserGroup;


import java.util.List;

/**
 * @author hejianglong
 * @date 2018/12/24.
 */

public class GetUserGroupListResponse extends PacketResponse {

    private List<UserGroup> userGroups;

    @Override
    public byte getCommand() {
        return Command.GET_USER_GROUP_LIST_RESPONSE;
    }

    public List<UserGroup> getUserGroups() {
        return userGroups;
    }

    public GetUserGroupListResponse setUserGroups(List<UserGroup> mUserGroups) {
        userGroups = mUserGroups;
        return this;
    }
}
