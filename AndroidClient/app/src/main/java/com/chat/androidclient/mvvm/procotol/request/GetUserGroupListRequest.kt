package com.chat.androidclient.mvvm.procotol.request

import com.chat.androidclient.mvvm.model.Command
import com.chat.androidclient.mvvm.procotol.Packet

/**
 * Created by lps on 2019/1/29 14:47.
 */
class GetUserGroupListRequest  : Packet() {
    override val command: Byte
        get() = Command.GET_USER_GROUP_LIST
}