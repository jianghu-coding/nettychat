package com.chat.androidclient.mvvm.procotol.request;


import com.chat.androidclient.mvvm.model.Command;
import com.chat.androidclient.mvvm.procotol.Packet;

/**
 * @author hejianglong
 * @date 2019/1/22.
 */

public class SearchFriendRequest extends Packet {
    public SearchFriendRequest(String mName, String mUsername, Pagination mPagination) {
        name = mName;
        username = mUsername;
        pagination = mPagination;
    }

    public SearchFriendRequest(String mName, String mUsername) {
        name = mName;
        username = mUsername;
    }

    public String getName() {
        return name;
    }

    public SearchFriendRequest setName(String mName) {
        name = mName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public SearchFriendRequest setUsername(String mUsername) {
        username = mUsername;
        return this;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public SearchFriendRequest setPagination(Pagination mPagination) {
        pagination = mPagination;
        return this;
    }

    private String name;

    private String username;

    private Pagination pagination;


    @Override
    public byte getCommand() {
        return Command.SEARCH_FRIEND;
    }
}
