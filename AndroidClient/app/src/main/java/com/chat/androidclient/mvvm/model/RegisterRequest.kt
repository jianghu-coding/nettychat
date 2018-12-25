package com.chat.androidclient.mvvm.model

import com.chat.androidclient.mvvm.procotol.Packet
import com.chat.androidclient.network.NeetClient

/**
 * Created by lps on 2018/12/25 10:27.
 */
class RegisterRequest(var name:String,var username:String, var password:String): Packet() {
    override val command: Byte
        get() = Command.REGISTER
    
    override fun toString(): String {
        return "RegisterRequest(name='$name', username='$username', password='$password')"
    }
    
}


