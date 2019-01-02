package com.chat.androidclient.mvvm.procotol.request

import com.chat.androidclient.mvvm.model.Command
import com.chat.androidclient.mvvm.procotol.Packet

/**
 * Created by lps on 2019/1/2 15:35.
 */
class SendMessageRequest(): Packet() {
    override val command: Byte
        get() = Command.SEND_MESSAGE
    var toUserId:Long=-1
    var message:String=""
    
    constructor(toUserId: Long, message: String) : this() {
        this.toUserId = toUserId
        this.message = message
    }
}