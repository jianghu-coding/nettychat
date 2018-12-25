package com.chat.androidclient.handler

import com.chat.androidclient.mvvm.model.PacketResponse
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

/**
 * Created by lps on 2018/12/25 13:27.
 */
class ResponseHandler : SimpleChannelInboundHandler<PacketResponse>() {
    override fun channelRead0(ctx: ChannelHandlerContext?, msg: PacketResponse) {
        if (msg.error){
        
        }
    }
}