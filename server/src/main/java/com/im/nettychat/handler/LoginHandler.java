package com.im.nettychat.handler;

import com.im.nettychat.executor.ThreadPoolService;
import com.im.nettychat.protocol.request.LoginRequest;
import com.im.nettychat.service.UserService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static com.im.nettychat.service.UserService.userService;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
@ChannelHandler.Sharable
public class LoginHandler extends SimpleChannelInboundHandler<LoginRequest> {

    public static final LoginHandler INSTANCE = new LoginHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequest msg) throws Exception {
        userService.login(ctx, msg);
    }
}
