package com.im.nettychat.handler;

import com.im.nettychat.common.Command;
import com.im.nettychat.protocol.Packet;
import com.im.nettychat.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/22 上午12:08
 */
@ChannelHandler.Sharable
public class IMHandler extends SimpleChannelInboundHandler<Packet> {

    public static final IMHandler INSTANCE = new IMHandler();

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(IMHandler.class);

    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    public IMHandler() {
        handlerMap = new HashMap<>();
        handlerMap.put(Command.SEND_MESSAGE, MessageHandler.INSTANCE);
        handlerMap.put(Command.CREATE_GROUP, CreateGroupHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        handlerMap.get(msg.getCommand()).channelRead(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("server exception: ", cause);
        ctx.channel().close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        boolean hashLogin = SessionUtil.hasLogin(ctx.channel());
        if (hashLogin) {
            SessionUtil.unBindSession(ctx.channel());
        }
        ctx.channel().close();
    }
}
