package com.chat.androidclient.mvvm.procotol.request;

import com.chat.androidclient.mvvm.model.Command;
import com.chat.androidclient.mvvm.procotol.Packet;

/**
 * @author hejianglong
 * @date 2019/1/30.
 */

public class SearchGroupRequest extends Packet {

    private String name;

    @Override
    public byte getCommand() {
        return Command.SEARCH_GROUP;
    }

    public SearchGroupRequest(String mName) {
        name = mName;
    }

    public String getName() {
        return name;
    }

    public SearchGroupRequest setName(String mName) {
        name = mName;
        return this;
    }
}
