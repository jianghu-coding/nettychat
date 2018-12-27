package com.chat.androidclient.handler

import com.chat.androidclient.event.LoginResponseEvent
import com.chat.androidclient.event.SignUpResponseEvent
import com.chat.androidclient.mvvm.model.Command
import com.chat.androidclient.mvvm.model.PacketResponse
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import org.greenrobot.eventbus.EventBus

/**
 * Created by lps on 2018/12/25 13:27.
 */
class ResponseHandler : SimpleChannelInboundHandler<PacketResponse>() {
 
    override fun channelRead0(ctx: ChannelHandlerContext, msg: PacketResponse) {
   when(msg.command){
       Command.LOGIN_RESPONSE->{
         EventBus.getDefault().post(LoginResponseEvent(msg))
       }
       Command.REGISTER_RESPONSE->{
           EventBus.getDefault().post(SignUpResponseEvent(msg))
       }
   }
    }
    
}