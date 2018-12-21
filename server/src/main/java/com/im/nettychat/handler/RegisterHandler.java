/*
 * Project: com.im.nettychat.handler
 * 
 * File Created at 2018/12/20
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

import com.im.nettychat.protocol.request.RegisterRequest;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import static com.im.nettychat.service.impl.UserServiceImpl.userService;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/20 下午9:36
 */
@ChannelHandler.Sharable
public class RegisterHandler extends SimpleChannelInboundHandler<RegisterRequest> {

    public static final RegisterHandler INSTANCE = new RegisterHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RegisterRequest msg) throws Exception {
        userService.register(ctx, msg);
    }
}
