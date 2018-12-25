package com.chat.androidclient.mvvm.model

import com.chat.androidclient.mvvm.procotol.Packet
import com.chat.androidclient.network.NeetClient

/**
 * Created by lps on 2018/12/25 10:27.
 */
class LoginRequest(var username:String,var password:String): Packet() {
    override val command: Byte
        get() = Command.LOGIN
    
}


