package com.chat.androidclient.mvvm.procotol

import com.blankj.utilcode.util.LogUtils
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageCodec
import io.netty.util.internal.logging.InternalLoggerFactory

@ChannelHandler.Sharable
class PacketCodecHandler private constructor() : MessageToMessageCodec<ByteBuf, Packet>() {

    override fun decode(ctx: ChannelHandlerContext, byteBuf: ByteBuf, out: MutableList<Any>) {
        val packet = PacketCodec.INSTANCE.decode(byteBuf)
        if (packet == null) {
            LogUtils.e("The command is not valid"+byteBuf)
            return
        }
        out.add(packet)
    }

    override fun encode(ctx: ChannelHandlerContext, packet: Packet, out: MutableList<Any>) {
        val byteBuf = ctx.channel().alloc().ioBuffer()
        PacketCodec.INSTANCE.encode(byteBuf, packet)
        out.add(byteBuf)
    }

    companion object {
        val INSTANCE = PacketCodecHandler()

        private val logger = InternalLoggerFactory.getInstance(PacketCodecHandler::class.java)
    }
}
