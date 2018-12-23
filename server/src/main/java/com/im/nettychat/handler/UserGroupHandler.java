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

import com.im.nettychat.common.Command;
import com.im.nettychat.protocol.request.group.CreateGroupRequest;
import com.im.nettychat.protocol.request.group.GetUserGroupRequest;
import com.im.nettychat.protocol.request.group.JoinGroupRequest;
import com.im.nettychat.protocol.request.group.SendGroupMessageRequest;
import com.im.nettychat.protocol.request.group.UserGroupRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import static com.im.nettychat.service.impl.GroupServiceImpl.groupService;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/22 下午10:24
 */
public class UserGroupHandler extends SimpleChannelInboundHandler<UserGroupRequest> {

    public static final UserGroupHandler INSTANCE = new UserGroupHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, UserGroupRequest msg) throws Exception {
        if (Command.CREATE_GROUP.equals(msg.getCommand())) {
            groupService.createGroup(ctx, (CreateGroupRequest) msg);
        } else if (Command.JOIN_GROUP.equals(msg.getCommand())) {
            groupService.joinGroup(ctx, (JoinGroupRequest) msg);
        } else if (Command.GET_USER_GROUP.equals(msg.getCommand())) {
            groupService.getUserGroup(ctx, (GetUserGroupRequest) msg);
        } else if (Command.SEND_GROUP_MESSAGE.equals(msg.getCommand())) {
            groupService.sendGroupMessage(ctx, (SendGroupMessageRequest) msg);
        }
    }
}
