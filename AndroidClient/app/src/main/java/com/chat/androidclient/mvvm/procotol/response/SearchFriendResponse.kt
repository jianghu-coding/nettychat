package com.chat.androidclient.mvvm.procotol.response

import com.chat.androidclient.mvvm.model.Command
import com.chat.androidclient.mvvm.model.PacketResponse
import com.chat.androidclient.mvvm.model.User
import java.io.Serializable

/**
 * Created by lps on 2019/1/23 15:38.
 */
class SearchFriendResponse() : PacketResponse() ,Serializable{
    var users:List<User> = listOf()
    override val command: Byte
        get() = Command.SEARCH_FRIEND_RESPONSE
}