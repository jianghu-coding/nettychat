package com.im.nettychat.handler;

import com.im.nettychat.common.ErrorCode;
import com.im.nettychat.config.ErrorConfig;
import com.im.nettychat.protocol.request.MessageRequest;
import com.im.nettychat.protocol.response.MessageResponse;
import com.im.nettychat.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import static com.im.nettychat.common.AttributeKeys.SESSION_ATTRIBUTE_KEY;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
@ChannelHandler.Sharable
public class MessageHandler extends SimpleChannelInboundHandler<MessageRequest> {

    public static final MessageHandler INSTANCE = new MessageHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequest request) throws Exception {
        MessageResponse response = new MessageResponse();

        String message = request.getMessage();
        Long toUserId = request.getToUserId();
        Long fromUserId = ctx.channel().attr(SESSION_ATTRIBUTE_KEY).get().getUserId();
        Channel toChannel = SessionUtil.getChannel(toUserId);
        if (fromUserId.equals(toUserId)) {
            return;
        }
        if (toChannel == null) {
            response.setError(true);
            response.setErrorInfo(ErrorConfig.getError(ErrorCode.NOT_SUPPORT_SEND_OFFLINE));
            ctx.writeAndFlush(response);
            return;
        }
        response.setMessage(message);
        response.setFromUserId(fromUserId);
        toChannel.writeAndFlush(response);
    }
}
