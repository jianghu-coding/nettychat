package com.im.nettychat.boot;

import com.im.nettychat.config.load.ConfigProperties;
import com.im.nettychat.config.ServerConfig;
import com.im.nettychat.util.DateUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
public class ServerStarter {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ServerStarter.class);

    public static void main(String[] args) throws IOException {
        ConfigProperties.initParam(args);
        start();
    }

    private static void start() {
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            final ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    .group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {

                        }
                    });
            serverBootstrap.bind(ServerConfig.getPort()).addListener(future -> {
                if (future.isSuccess()) {
                    logger.info(DateUtil.nowString() + " 服务器启动成功");
                    logger.info("listen: " + ServerConfig.getPort());
                } else {
                    logger.error("启动失败");
                }
            });
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
