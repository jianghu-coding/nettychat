package com.im.nettychat.handler;

import com.im.nettychat.common.Command;
import com.im.nettychat.protocol.Packet;
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
        handlerMap.put(Command.CREATE_GROUP, UserGroupHandler.INSTANCE);
        handlerMap.put(Command.GET_USER_GROUP, UserGroupHandler.INSTANCE);
        handlerMap.put(Command.JOIN_GROUP, UserGroupHandler.INSTANCE);
        handlerMap.put(Command.SEND_GROUP_MESSAGE, UserGroupHandler.INSTANCE);
        handlerMap.put(Command.GET_USER_GROUP_LIST, UserGroupHandler.INSTANCE);
        handlerMap.put(Command.ADD_FRIEND, UserHandler.INSTANCE);
        handlerMap.put(Command.GET_FRIENDS, UserHandler.INSTANCE);
        handlerMap.put(Command.SEARCH_FRIEND, UserHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        SimpleChannelInboundHandler<? extends Packet> handler = handlerMap.get(msg.getCommand());
        if (handler == null) {
            logger.info("not found handler for command: " + msg.getCommand());
            return;
        }
        handlerMap.get(msg.getCommand()).channelRead(ctx, msg);
    }
}
