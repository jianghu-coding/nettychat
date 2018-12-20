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

import com.im.nettychat.protocol.Packet;
import com.im.nettychat.protocol.PacketCodec;
import com.im.nettychat.protocol.request.RegisterRequest;
import com.im.nettychat.protocol.response.LoginResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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
        Socket socket = new Socket("127.0.0.1", 8080);
        OutputStream out = socket.getOutputStream();
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName("along");
        registerRequest.setUsername("hejianglong");
        registerRequest.setPassword("123321893");
        out.write(getBytes(registerRequest));
        out.flush();
    }

    private byte[] getBytes(Packet packet) {
        ByteBuf byteBuf = Unpooled.buffer();
        PacketCodec.INSTANCE.encode(byteBuf, packet);
        return byteBuf.array();
    }
}
