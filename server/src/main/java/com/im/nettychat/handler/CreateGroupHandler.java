/*
 * Project: com.im.nettychat.handler
 * 
 * File Created at 2018/12/22
 * 
 * Copyright 2018 CMCC Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * ZYHY Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license.
 */
package com.im.nettychat.handler;

import com.im.nettychat.protocol.request.CreateGroupRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import static com.im.nettychat.service.impl.GroupServiceImpl.groupService;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/22 下午10:24
 */
public class CreateGroupHandler extends SimpleChannelInboundHandler<CreateGroupRequest> {

    public static final CreateGroupHandler INSTANCE = new CreateGroupHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequest msg) throws Exception {
        groupService.createGroup(ctx, msg);
    }
}
