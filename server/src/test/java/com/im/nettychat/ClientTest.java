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
import io.netty.handler.codec.MessageToMessageDecoder;
import org.junit.Test;
import java.io.IOException;
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

    /**
     * 测试注册
     * 安卓或者IOS传输的时候注意构造对象
     * 的Command 指令
     * @throws IOException
     */
    @Test
    public void testRegister() throws IOException, InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        bootstrap
            .group(workerGroup)
            .channel(NioSocketChannel.class)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .option(ChannelOption.TCP_NODELAY, true)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) {
                    // 收到服务器返回来的消息
                    ch.pipeline().addLast(new PacketDecoder());
                    ch.pipeline().addLast(new ResponseHandler());
                }
            });
        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8080).addListener(future -> {
            // 建立连接成功后发起登录请求或者注册请求
            Channel channel = ((ChannelFuture) future).channel();
            // 发起注册
            register(channel);
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
        for(int i = 0; i < thread; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Bootstrap bootstrap = new Bootstrap();
                        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
                        bootstrap
                            .group(workerGroup)
                            .channel(NioSocketChannel.class)
                            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                            .option(ChannelOption.SO_KEEPALIVE, true)
                            .option(ChannelOption.TCP_NODELAY, true)
                            .handler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                public void initChannel(SocketChannel ch) {
                                    // 收到服务器返回来的消息
                                    ch.pipeline().addLast(new PacketDecoder());
                                    ch.pipeline().addLast(new ResponseHandler());
                                }
                            });
                        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8080).addListener(future -> {
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
                                register(channel);
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

    private void register(Channel channel) {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName("along");
        registerRequest.setUsername("12038321");
        registerRequest.setPassword("1238765");
        ByteBuf byteBuf = channel.alloc().buffer();
        PacketCodec.INSTANCE.encode(byteBuf, registerRequest);
        channel.writeAndFlush(byteBuf);
        waitForLoginResponse();
    }


    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}

class ResponseHandler extends SimpleChannelInboundHandler<RegisterResponse> {

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

class PacketDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) {
        out.add(PacketCodec.INSTANCE.decode(in));
    }
}
