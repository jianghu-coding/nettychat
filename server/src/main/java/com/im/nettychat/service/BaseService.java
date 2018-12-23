/*
 * Project: com.im.nettychat.service
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
package com.im.nettychat.service;

import com.im.nettychat.config.ErrorConfig;
import com.im.nettychat.protocol.ResponsePacket;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/23 下午12:46
 */
public class BaseService {

    protected void exceptionResponse(ChannelHandlerContext ctx, String errorCode, ResponsePacket response) {
        response.setError(true);
        response.setErrorInfo(ErrorConfig.getError(errorCode));
        ctx.writeAndFlush(response);
    }
}
