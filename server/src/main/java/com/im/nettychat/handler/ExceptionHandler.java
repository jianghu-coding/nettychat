/*
 * Project: com.im.nettychat.handler
 * 
 * File Created at 2018/12/30
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

import com.im.nettychat.config.ServerConfig;
import com.im.nettychat.protocol.response.ReadTimeoutExceptionResponse;
import com.im.nettychat.protocol.response.WriteTimeoutExceptionResponse;
import com.im.nettychat.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.timeout.WriteTimeoutException;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * @author hejianglong
 * @date 2018/12/30 上午9:55
 * @email hejlong@163.com
 * @Desc
 */
@ChannelHandler.Sharable
public class ExceptionHandler extends ChannelInboundHandlerAdapter {

    public static final ExceptionHandler INSTANCE = new ExceptionHandler();

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(IMHandler.class);

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("server exception: ", cause);
        if (cause instanceof ReadTimeoutException) {
            // 长时间没有进行读操作
            ReadTimeoutExceptionResponse readTimeOutExceptionResponse = new ReadTimeoutExceptionResponse();
            readTimeOutExceptionResponse.setMessage(ServerConfig.getServerReadTimeout() + "秒没有进行读操作连接关闭, 请做心跳检测");
            ctx.writeAndFlush(readTimeOutExceptionResponse).addListener(future -> {
                if (future.isSuccess()) {
                    SessionUtil.unBindSession(ctx.channel());
                    ctx.channel().close();
                }
            });
        } else if (cause instanceof WriteTimeoutException) {
            // 长时间没有进行写操作
            WriteTimeoutExceptionResponse writeTimeoutExceptionResponse = new WriteTimeoutExceptionResponse();
            writeTimeoutExceptionResponse.setMessage(ServerConfig.getServerWriteTimeout() + "秒没有进行写操作连接关闭, 请做心跳检测");
            ctx.writeAndFlush(writeTimeoutExceptionResponse).addListener(future -> {
                if (future.isSuccess()) {
                    SessionUtil.unBindSession(ctx.channel());
                    ctx.channel().close();
                }
            });
        } else {
            cause.printStackTrace();
            SessionUtil.unBindSession(ctx.channel());
            ctx.channel().close();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        boolean hashLogin = SessionUtil.hasLogin(ctx.channel());
        if (hashLogin) {
            SessionUtil.unBindSession(ctx.channel());
        }
        ctx.channel().close();
    }
}
