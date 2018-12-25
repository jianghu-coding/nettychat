package com.chat.androidclient.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log
import com.blankj.utilcode.util.ToastUtils
import com.chat.androidclient.handler.ResponseHandler
import com.chat.androidclient.handler.VerifyHandler
import com.chat.androidclient.mvvm.model.Command
import com.chat.androidclient.mvvm.procotol.Packet
import com.chat.androidclient.mvvm.procotol.PacketCodecHandler
import io.netty.bootstrap.Bootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel


/**
 * Created by lps on 2018/12/25 11:08.
 */
class ChatService: Service() {
    companion object {
        val CMD="cmd"
        val EXTRA="extra"
        val CHAT_ACTION="chatcommand"
    }
     private lateinit var bootstrap:Bootstrap
     private  var channel:Channel?=null
    private val HOST = "192.168.0.145"
    private val TAG = "ChatService"
    private val PORT = 8888
    private lateinit var broadcastReceiver :BroadcastReceiver
    
    override fun onCreate() {
       bootstrap =Bootstrap()
        bootstrap.group(NioEventLoopGroup())
                .channel(NioSocketChannel::class.java)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(object : ChannelInitializer<SocketChannel>() {
                    override fun initChannel(ch: SocketChannel) {
                        Log.e(TAG,"initChannel"+ch.toString())
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
        val intentFilter = IntentFilter()
        intentFilter.addAction("chatcommand")
         broadcastReceiver = object :BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                val cmd = intent.getByteExtra(CMD,-1)
                when(cmd){
                    Command.LOGIN->{
                        val request = intent.getSerializableExtra(EXTRA)  as Packet
                        cmd(request)
                    }
                    Command.REGISTER->{
                        val request = intent.getSerializableExtra(EXTRA)  as Packet
                        cmd(request)
                    }
                }
            }
        }
         registerReceiver(broadcastReceiver, intentFilter)
    }
    
    override fun onBind(intent: Intent?): IBinder? {
    return null
    }
    
    fun cmd(request:Packet){
        if (channel==null){
            ToastUtils.showShort("channel为空,和服务器通信失败")
            return
        }
//        val byteBuf = channel!!.alloc().buffer()
//        PacketCodec.INSTANCE.encode(byteBuf,request)
        Log.e(TAG,"cmd+\t"+request.toString())
        channel!!.writeAndFlush(request).addListener {
            if (it.isSuccess){
                Log.e(TAG,"writeAndFlush成功")
            }else{
                Log.e(TAG,"writeAndFlush失败")
    
            }
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }
}


