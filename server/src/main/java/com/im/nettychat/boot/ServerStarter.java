package com.im.nettychat.boot;

import com.im.nettychat.cache.RedisBootstrap;
import com.im.nettychat.codec.PacketCodecHandler;
import com.im.nettychat.config.load.ConfigProperties;
import com.im.nettychat.config.ServerConfig;
import com.im.nettychat.executor.ThreadPoolService;
import com.im.nettychat.handler.AuthHandler;
import com.im.nettychat.handler.IMHandler;
import com.im.nettychat.handler.LoginHandler;
import com.im.nettychat.handler.RegisterHandler;
import com.im.nettychat.handler.RequestMessageHandler;
import com.im.nettychat.handler.ServerVerifyHandler;
import com.im.nettychat.util.DateUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
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

    private static NioEventLoopGroup boosGroup = new NioEventLoopGroup();

    private static NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    public static void main(String[] args) throws IOException, InterruptedException {
        init(args);
        start();
    }

    private static void start() throws InterruptedException {
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
                            ch.pipeline().addLast(new ServerVerifyHandler());
                            ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
                            ch.pipeline().addLast(RegisterHandler.INSTANCE);
                            ch.pipeline().addLast(LoginHandler.INSTANCE);
                            // 后面的都必须登录后访问
                            ch.pipeline().addLast(AuthHandler.INSTANCE);
                            ch.pipeline().addLast(RequestMessageHandler.INSTANCE);
                            ch.pipeline().addLast(IMHandler.INSTANCE);
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(ServerConfig.getPort()).sync();
            channelFuture.addListener(future -> {
                if (future.isSuccess()) {
                    logger.info(DateUtil.nowString() + " 服务器启动成功");
                    logger.info("listen: " + ServerConfig.getPort());
                } else {
                    logger.error("启动失败");
                }
            });
            channelFuture.channel().closeFuture().sync();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            ThreadPoolService.getExecutorService().shutdown();
        }
    }

    private static void init(String[] args) throws IOException {
        ConfigProperties.initParam(args);
        RedisBootstrap.init();
    }
}
