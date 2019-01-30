package com.im.nettychat.service;

import com.im.nettychat.protocol.request.ResourceRequest;
import io.netty.channel.ChannelHandlerContext;
/**
 * @author hejianglong
 * @date 2019/1/30.
 */
public interface ResourceService {
    void findValuesByType(ChannelHandlerContext ctx, ResourceRequest request);
}
