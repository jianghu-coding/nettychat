/*
 * Project: com.im.nettychat.handler
 * 
 * File Created at 2018/12/22
 * 
 * Copyright 2018 CMCC Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * ZYHY Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license.
 */
package com.chat.androidclient.handler

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.LengthFieldBasedFrameDecoder

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/22 下午1:58
 */
class VerifyHandler : LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH) {

    @Throws(Exception::class)
    override fun decode(ctx: ChannelHandlerContext, `in`: ByteBuf): Any? {
        if (`in`.getInt(`in`.readerIndex()) != PacketCodec.MAGIC_NUMBER) {
            ctx.channel().close()
            return null
        }
        return super.decode(ctx, `in`)
    }

    companion object {

        private val LENGTH_FIELD_OFFSET = 7

        private val LENGTH_FIELD_LENGTH = 4
    }
}
