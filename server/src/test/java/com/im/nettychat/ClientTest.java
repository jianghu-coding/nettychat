/*
 * Project: com.im.nettychat
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
package com.im.nettychat;

import com.im.nettychat.common.Command;
import com.im.nettychat.protocol.PacketCodec;
import com.im.nettychat.protocol.request.RegisterRequest;
import com.im.nettychat.protocol.response.RegisterResponse;
import com.im.nettychat.util.Util;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/20 下午10:08
 */
public class ClientTest {

    public static final String TEST_A_USERNAME = "666666";
    public static final String TEST_A_PASSWORD = "777777";
    public static final String TEST_A_NAME = "阿宝";
    public static final String TEST_B_USERNAME = "888888";
    public static final String TEST_B_PASSWORD = "999999";
    public static final String TEST_B_NAME = "宝六";
    public static final String HOST = "127.0.0.1";

    /**
     * 测试注册
     * 安卓或者IOS传输的时候注意构造对象
     * 的Command 指令
     * @throws IOException
     */
    @Test
    public void testRegister() throws IOException, InterruptedException {
        Bootstrap bootstrap = connectServer();
        bootstrap
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(new PacketDecoder());
                    ch.pipeline().addLast(new RegisterResponseHandler());
                }
            });
        final String username = TEST_A_USERNAME;
        final String password = TEST_A_PASSWORD;
        final String name = TEST_A_NAME;
        ChannelFuture channelFuture = bootstrap.connect(HOST, 8080).addListener(future -> {
            // 建立连接成功后发起登录请求或者注册请求
            Channel channel = ((ChannelFuture) future).channel();
            // 发起注册
            register(channel, name, username, password);
        });
        channelFuture.sync().channel().closeFuture().sync();
    }

    // 并发测试
    @Test
    public void pressureTestRegister() throws InterruptedException {
        int thread = 100;
        CountDownLatch countDownLatch = new CountDownLatch(thread);
        CountDownLatch end = new CountDownLatch(1);
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        final String username = "";
        final String password = "";
        final String name = "";
        for(int i = 0; i < thread; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Bootstrap bootstrap = connectServer();
                        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                public void initChannel(SocketChannel ch) {
                                    // 收到服务器返回来的消息
                                    ch.pipeline().addLast(new PacketDecoder());
                                    ch.pipeline().addLast(new RegisterResponseHandler());
                                    ch.pipeline().addLast(new LoginResponseHandler());
                                }
                            });
                        ChannelFuture channelFuture = bootstrap.connect(HOST, 8080).addListener(future -> {
                            if (future.isSuccess()) {
                                // 建立连接成功后发起登录请求或者注册请求
                                Channel channel = ((ChannelFuture) future).channel();
                                System.out.println("开始等待");
                                // 连接成功 -1
                                countDownLatch.countDown();
                                // 等待所有客户端连接成功
                                countDownLatch.await();
                                System.out.println("并发注册");
                                // 客户端同事发起发起注册
                                register(channel, name, username, password);
                            }
                        });
                        try {
                            channelFuture.sync().channel().closeFuture().sync();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        // 不结束
        end.await();
    }

    private void register(Channel channel, String name, String username, String password) {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName(name);
        registerRequest.setUsername(username);
        registerRequest.setPassword(password);
        ByteBuf byteBuf = channel.alloc().buffer();
        PacketCodec.INSTANCE.encode(byteBuf, registerRequest);
        channel.writeAndFlush(byteBuf);
        waitForResponse();
    }


    @Test
    public void login() {
        String oldp = Util.hashPasswordAddingSalt("3683989");
        boolean s = Util.isValidPassword("3683989", oldp);
        System.out.println(s);
    }


    private static void waitForResponse() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
    }

    private static Bootstrap connectServer() {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        bootstrap
            .group(workerGroup)
            .channel(NioSocketChannel.class)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .option(ChannelOption.TCP_NODELAY, true);
        return bootstrap;
    }
}

class RegisterResponseHandler extends SimpleChannelInboundHandler<RegisterResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RegisterResponse msg) throws Exception {
        if (msg.getCommand() == Command.REGISTER_RESPONSE) {
            // 如果是注册则返回注册响应指令
            RegisterResponse registerResponse = (RegisterResponse) msg;
            // 如果存在错误
            if (registerResponse.isError()) {
                System.out.println(registerResponse.getErrorInfo());
            } else {
                // 成功输出对象
                System.out.println(registerResponse);
            }
        }
    }
}