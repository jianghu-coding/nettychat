package com.im.nettychat.handler;

import com.im.nettychat.protocol.response.ForbiddenResponse;
import com.im.nettychat.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/22 下午3:57
 */
@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {

    public static final AuthHandler INSTANCE = new AuthHandler();

    private AuthHandler() {}

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (SessionUtil.notLogin(ctx.channel())) {
            ForbiddenResponse forbiddenResponse = new ForbiddenResponse();
            forbiddenResponse.setMessage("请登录后访问, 服务断开连接");
            ctx.writeAndFlush(forbiddenResponse).addListener(future -> {
                if (future.isSuccess()) {
                    ctx.channel().close();
                }
            });
        } else {
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }
}
