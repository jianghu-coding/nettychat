package com.im.nettychat.handler;

import com.im.nettychat.executor.ThreadPoolService;
import com.im.nettychat.protocol.request.LoginRequest;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
@ChannelHandler.Sharable
public class LoginHandler extends SimpleChannelInboundHandler<LoginRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequest msg) throws Exception {
        execute(ctx, msg);
    }

    private void execute(ChannelHandlerContext ctx, LoginRequest msg) {
        ThreadPoolService.execute(new Runnable() {
            @Override
            public void run() {
            }
        });
    }
}
