package com.im.nettychat.handler;

import com.im.nettychat.protocol.request.ResourceRequest;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import static com.im.nettychat.service.impl.ResourceServiceImpl.resourceService;

/**
 * @author hejianglong
 * @date 2019/1/30.
 */
@ChannelHandler.Sharable
public class ResourceHandler extends SimpleChannelInboundHandler<ResourceRequest> {

    public static final ResourceHandler INSTANCE = new ResourceHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ResourceRequest request) throws Exception {
        resourceService.findValuesByType(ctx, request);
    }
}
