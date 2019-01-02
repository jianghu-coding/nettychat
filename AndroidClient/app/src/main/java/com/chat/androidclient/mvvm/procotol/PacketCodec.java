package com.chat.androidclient.mvvm.procotol;



import com.blankj.utilcode.util.LogUtils;
import com.chat.androidclient.mvvm.model.Command;
import com.chat.androidclient.mvvm.model.LoginRequest;
import com.chat.androidclient.mvvm.procotol.response.MessageResponse;
import com.chat.androidclient.mvvm.model.RegisterRequest;
import com.chat.androidclient.mvvm.procotol.response.RegisterResponse;
import com.chat.androidclient.mvvm.procotol.response.GetFriendResponse;
import com.chat.androidclient.mvvm.procotol.response.LoginResponse;
import com.chat.androidclient.serialize.Serializer;
import com.chat.androidclient.serialize.impl.JSONSerializer;

import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public class PacketCodec {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(PacketCodec.class);

    public static final int MAGIC_NUMBER = 0x28917645;
    public static final PacketCodec INSTANCE = new PacketCodec();
    private final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;


    private PacketCodec() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.LOGIN, LoginRequest.class);
        packetTypeMap.put(Command.REGISTER, RegisterRequest.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponse.class);
        packetTypeMap.put(Command.REGISTER_RESPONSE, RegisterResponse.class);
        packetTypeMap.put(Command.SEND_MESSAGE_RESPONSE, MessageResponse.class);
//        packetTypeMap.put(Command.CREATE_GROUP, CreateGroupRequest.class);
//        packetTypeMap.put(Command.JOIN_GROUP, JoinGroupRequest.class);
//        packetTypeMap.put(Command.GET_USER_GROUP, GetUserGroupRequest.class);
//        packetTypeMap.put(Command.SEND_GROUP_MESSAGE, SendGroupMessageRequest.class);
//        packetTypeMap.put(Command.ADD_FRIEND, AddFriendRequest.class);
        packetTypeMap.put(Command.GET_FRIENDS, GetFriendRequest.class);
        packetTypeMap.put(Command.GET_FRIENDS_RESPONSE, GetFriendResponse.class);
//        packetTypeMap.put(Command.GET_USER_GROUP_LIST, GetUserGroupListRequest.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }



    public void encode(ByteBuf byteBuf, Packet packet) {
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

    public Packet decode(ByteBuf byteBuf) {
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();
        LogUtils.e("command: " + command);

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {

        return packetTypeMap.get(command);
    }
}
