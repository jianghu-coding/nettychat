/*
 * Project: com.im.nettychat.service
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
package com.im.nettychat.service;

import com.im.nettychat.protocol.request.LoginRequest;
import com.im.nettychat.protocol.request.RegisterRequest;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/20 下午8:21
 */
public interface UserService {

    void login(ChannelHandlerContext ctx, LoginRequest msg);

    void register(ChannelHandlerContext ctx, RegisterRequest msg);
}
