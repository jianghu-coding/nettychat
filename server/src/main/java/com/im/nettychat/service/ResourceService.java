package com.im.nettychat.service;

import com.im.nettychat.protocol.request.SearchResourceRequest;
import io.netty.channel.ChannelHandlerContext;
/**
 * @author hejianglong
 * @date 2019/1/30.
 */
public interface ResourceService {
    void findValuesByType(ChannelHandlerContext ctx, SearchResourceRequest request);
}
