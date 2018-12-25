package com.chat.androidclient.mvvm.procotol

import com.chat.androidclient.mvvm.model.Command
import com.chat.androidclient.mvvm.model.LoginRequest
import com.chat.androidclient.serialize.Serializer
import com.chat.androidclient.serialize.impl.JSONSerializer
import io.netty.buffer.ByteBuf

/**
 * Created by lps on 2018/12/25 10:43.
 */
class PacketCodec private constructor(){
    
    companion object {
        val INSTANCE = PacketCodec()
    }
   private val MAGIC_NUMBER = 0x28917645
    private var packetTypeMap: Map<Byte, Class<out Packet>>
    private var serializerMap: Map<Byte, Serializer>
    
    
    init {
        packetTypeMap= hashMapOf(Command.LOGIN to LoginRequest::class.java
//        todo 新的接口在这里加入
        )
        val jsonSerializer = JSONSerializer()
        serializerMap= hashMapOf(jsonSerializer.serializerAlgorithm to jsonSerializer)
    }
    
    fun encode (byteBuf:ByteBuf, packet: Packet){
        val bytes = Serializer.DEFAULT.serialize(packet)
        byteBuf.writeInt(MAGIC_NUMBER)
        byteBuf.writeByte(packet.version)
        byteBuf.writeByte(Serializer.DEFAULT.serializerAlgorithm.toInt())
        byteBuf.writeByte(packet.command.toInt())
        byteBuf.writeByte(bytes.size)
        byteBuf.writeBytes(bytes)
    }
    
    fun  decode(){}
}