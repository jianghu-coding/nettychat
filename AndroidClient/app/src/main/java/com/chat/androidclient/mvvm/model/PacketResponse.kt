package com.chat.androidclient.mvvm.model

import com.chat.androidclient.mvvm.procotol.Packet

/**
 * Created by lps on 2018/12/25 13:28.
 */
abstract class PacketResponse : Packet() {
     var error: Boolean = false
    
     var errorInfo: String? = null
}