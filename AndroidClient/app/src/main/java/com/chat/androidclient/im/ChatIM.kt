package com.chat.androidclient.im

import android.util.Log
import com.blankj.utilcode.util.ToastUtils
import com.chat.androidclient.handler.ResponseHandler
import com.chat.androidclient.handler.VerifyHandler
import com.chat.androidclient.mvvm.procotol.Packet
import com.chat.androidclient.mvvm.procotol.PacketCodecHandler
import io.netty.bootstrap.Bootstrap
import io.netty.channel.*
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel

/**
 * Created by lps on 2018/12/25 16:03.
 */
class ChatIM private constructor() {
    companion object {
        
        val instance = SignletonHolder.holder
        private lateinit var bootstrap: Bootstrap
        private var channel: Channel? = null
        private val HOST = "114.115.248.101"
        private val TAG = "ChatService"
        private val PORT = 8080
    
        /**
         * 初始化
         */
        
        fun init() {
            bootstrap = Bootstrap()
            bootstrap.group(NioEventLoopGroup())
                    .channel(NioSocketChannel::class.java)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(object : ChannelInitializer<SocketChannel>() {
                        override fun initChannel(ch: SocketChannel) {
                            Log.e(TAG, "initChannel" + ch.toString())
                            ch.pipeline().addLast(VerifyHandler())
                            ch.pipeline().addLast(PacketCodecHandler.INSTANCE)
                            // 收到服务器返回来的消息
                            ch.pipeline().addLast(ResponseHandler())
                        }
                        
                        
                    })
            bootstrap.connect(HOST, PORT).addListener {
                if (it.isSuccess) {
                    channel = (it as ChannelFuture).channel()
                    Log.e(TAG, "连接成功")
                }
                else {
                    Log.e(TAG, "连接失败")
                }
            }
        }
    }
    
    private object SignletonHolder {
        val holder = ChatIM()
        
    }
    
    fun cmd(request: Packet) {
        if (channel == null) {
            ToastUtils.showShort("channel为空,和服务器通信失败")
            return
        }
        Log.e(TAG, "cmd+\t" + request.toString())
        channel!!.writeAndFlush(request)
    }
    
    fun cmd(request:   Packet, listener: CallBack) {
        if (channel == null) {
            ToastUtils.showShort("channel为空,和服务器通信失败")
            return
        }
        Log.e(TAG, "cmd+\t" + request.toString())
        channel!!.writeAndFlush(request)
        
    }

    
    interface CallBack {
        fun success()
        fun failed()
    }
    
}