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

import com.im.nettychat.config.ServerConfig;
import com.im.nettychat.util.DateUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import static com.im.nettychat.executor.AsyncTaskPool.TASK_POOL;

/**
 * http协议
 * @author hejianglong
 * @Desc
 * @date 2018/12/24 下午9:57
 */
public class HttpStarter implements Runnable {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(HttpStarter.class);

    private static volatile boolean exit = false;

    @Override
    public void run() {
        while (!Thread.interrupted() && !exit) {
            start();
        }
    }

    private static void start() {
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            final ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                .group(boosGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new HttpChannelInitializer());
            ChannelFuture channelFuture = serverBootstrap.bind(ServerConfig.getHttpPort()).sync();
            channelFuture.addListener(future -> {
                if (future.isSuccess()) {
                    logger.info(DateUtil.nowString() + " 服务器启动成功");
                    logger.info("http server listen: " + ServerConfig.getHttpPort());
                } else {
                    logger.error("启动失败");
                }
            });
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            TASK_POOL.shutdown();
            Thread.currentThread().interrupt();
            exit = true;
        }
    }
}
