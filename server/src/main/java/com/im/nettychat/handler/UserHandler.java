/*
 * Project: com.im.nettychat.handler
 * 
 * File Created at 2018/12/23
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

import com.im.nettychat.common.Command;
import com.im.nettychat.protocol.request.user.AddFriendRequest;
import com.im.nettychat.protocol.request.user.GetFriendRequest;
import com.im.nettychat.protocol.request.user.UserRequest;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import static com.im.nettychat.service.impl.UserServiceImpl.userService;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/23 下午8:47
 */
@ChannelHandler.Sharable
public class UserHandler extends SimpleChannelInboundHandler<UserRequest> {

    public static final UserHandler INSTANCE = new UserHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, UserRequest msg) throws Exception {
        if (Command.ADD_FRIEND.equals(msg.getCommand())) {
            userService.addFriend(ctx, (AddFriendRequest) msg);
        } else if (Command.GET_FRIENDS.equals(msg.getCommand())) {
            userService.getFriends(ctx, (GetFriendRequest) msg);
        }
    }
}
