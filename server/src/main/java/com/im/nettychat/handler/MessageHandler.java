package com.im.nettychat.handler;

import com.im.nettychat.protocol.request.MessageRequest;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import static com.im.nettychat.service.impl.UserServiceImpl.userService;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
@ChannelHandler.Sharable
public class MessageHandler extends SimpleChannelInboundHandler<MessageRequest> {

    public static final MessageHandler INSTANCE = new MessageHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequest request) throws Exception {
        userService.sendMessage(ctx, request);
    }
}
