package com.chat.androidclient.mvvm.procotol;

import com.chat.androidclient.mvvm.model.Command;

/**
 * Created by lps on 2018/12/29 17:36.
 */
public class GetFriendRequest extends Packet {
    @Override
    public byte getCommand() {
        return Command.GET_FRIENDS;
    }
}
