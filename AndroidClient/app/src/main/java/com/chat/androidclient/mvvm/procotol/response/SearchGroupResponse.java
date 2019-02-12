package com.chat.androidclient.mvvm.procotol.response;

import com.chat.androidclient.mvvm.model.Command;
import com.chat.androidclient.mvvm.model.GroupDTO;
import com.chat.androidclient.mvvm.model.PacketResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hejianglong
 * @date 2019/1/30.
 */

public class SearchGroupResponse extends PacketResponse {

    private List<GroupDTO> groups=new ArrayList<>();

    @Override
    public byte getCommand() {
        return Command.SEARCH_GROUP_RESPONSE;
    }

    public List<GroupDTO> getGroups() {
        return groups;
    }

    public SearchGroupResponse setGroups(List<GroupDTO> mGroups) {
        groups = mGroups;
        return this;
    }
}
