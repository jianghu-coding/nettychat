package com.im.nettychat.handler;

import com.im.nettychat.protocol.request.LoginRequest;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import static com.im.nettychat.service.impl.UserServiceImpl.userService;


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
