package com.im.nettychat.handler;

import com.im.nettychat.protocol.request.MessageRequest;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
@ChannelHandler.Sharable
public class RequestMessageHandler extends SimpleChannelInboundHandler<MessageRequest> {

    public static final RequestMessageHandler INSTANCE = new RequestMessageHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequest msg) throws Exception {
        System.out.println("");
    }
}
