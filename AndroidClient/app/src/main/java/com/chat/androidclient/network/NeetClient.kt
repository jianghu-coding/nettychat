package com.chat.androidclient.network

import com.chat.androidclient.mvvm.model.LoginRequest
import io.netty.channel.Channel

/**
 * Created by lps on 2018/12/25 10:24.
 */
object  NeetClient{
    val CMD_LOGIN:Byte=1
 fun login(chanel:Channel,name:String,pass:String){
     val request = LoginRequest(name, pass)
     val byteBuf = chanel.alloc().buffer()
     PacketCodec.INSTANCE.encode(byteBuf,request)
     chanel.writeAndFlush(byteBuf)
 }
}