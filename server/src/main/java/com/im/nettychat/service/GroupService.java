/*
 * Project: com.im.nettychat.service
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
package com.im.nettychat.service;

import com.im.nettychat.protocol.request.CreateGroupRequest;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/22 下午10:32
 */
public interface GroupService {
    void createGroup(ChannelHandlerContext ctx, CreateGroupRequest msg);
}
