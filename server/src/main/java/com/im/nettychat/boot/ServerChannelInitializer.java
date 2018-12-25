/*
 * Project: com.im.nettychat.boot
 * 
 * File Created at 2018/12/24
 * 
 * Copyright 2018 CMCC Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * ZYHY Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license.
 */
package com.im.nettychat.boot;

import com.im.nettychat.codec.PacketCodecHandler;
import com.im.nettychat.handler.AuthHandler;
import com.im.nettychat.handler.IMHandler;
import com.im.nettychat.handler.LoginHandler;
import com.im.nettychat.handler.MessageHandler;
import com.im.nettychat.handler.RegisterHandler;
import com.im.nettychat.handler.VerifyHandler;
import com.im.nettychat.handler.http.HttpRequestHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/24 下午9:36
 */
public class ServerChannelInitializer extends ChannelInitializer<NioSocketChannel> {

    @Override
    protected void initChannel(NioSocketChannel ch) {
        ch.pipeline().addLast(new VerifyHandler());
        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
        ch.pipeline().addLast(RegisterHandler.INSTANCE);
        ch.pipeline().addLast(LoginHandler.INSTANCE);
        // 后面的都必须登录后访问
        ch.pipeline().addLast(AuthHandler.INSTANCE);
        ch.pipeline().addLast(MessageHandler.INSTANCE);
        ch.pipeline().addLast(IMHandler.INSTANCE);
    }
}
