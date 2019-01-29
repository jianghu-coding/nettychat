package com.chat.androidclient.mvvm.procotol.request

import com.chat.androidclient.mvvm.model.Command
import com.chat.androidclient.mvvm.procotol.Packet

/**
 * Created by lps on 2019/1/29 10:09.
 */
class CreateGroupRequest(var groupName: String) : Packet() {
    var userIds= listOf<Long>()
    
    override val command: Byte
        get() = Command.CREATE_GROUP
}