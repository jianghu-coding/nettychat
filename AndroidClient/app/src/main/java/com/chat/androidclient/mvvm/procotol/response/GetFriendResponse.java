package com.chat.androidclient.mvvm.procotol.response;

import com.chat.androidclient.mvvm.model.Command;
import com.chat.androidclient.mvvm.model.PacketResponse;
import com.chat.androidclient.mvvm.model.User;

import java.util.List;

/**
 * Created by lps on 2018/12/29 17:41.
 */
public class GetFriendResponse extends PacketResponse {
    private List<User> friends;

    @Override
    public byte getCommand() {
        return Command.GET_FRIENDS_RESPONSE;
    }

    public List<User> getFriends() {
        return friends;
    }

    public GetFriendResponse setFriends(List<User> mFriends) {
        friends = mFriends;
        return this;
    }
}
