package com.im.nettychat.protocol;

import com.im.nettychat.common.Command;
import com.im.nettychat.protocol.request.group.CreateGroupRequest;
import com.im.nettychat.protocol.request.LoginRequest;
import com.im.nettychat.protocol.request.MessageRequest;
import com.im.nettychat.protocol.request.RegisterRequest;
import com.im.nettychat.protocol.request.group.GetUserGroupListRequest;
import com.im.nettychat.protocol.request.group.GetUserGroupRequest;
import com.im.nettychat.protocol.request.group.JoinGroupRequest;
import com.im.nettychat.protocol.request.group.SendGroupMessageRequest;
import com.im.nettychat.protocol.request.user.AddFriendRequest;
import com.im.nettychat.protocol.request.user.GetFriendRequest;
import com.im.nettychat.protocol.response.group.CreateGroupResponse;
import com.im.nettychat.protocol.response.LoginResponse;
import com.im.nettychat.protocol.response.MessageResponse;
import com.im.nettychat.protocol.response.RegisterResponse;
import com.im.nettychat.protocol.response.group.GetUserGroupListResponse;
import com.im.nettychat.protocol.response.group.GetUserGroupResponse;
import com.im.nettychat.protocol.response.group.JoinGroupResponse;
import com.im.nettychat.protocol.response.group.SendGroupMessageResponse;
import com.im.nettychat.protocol.response.offline.OfflineMessageResponse;
import com.im.nettychat.protocol.response.user.AddFriendResponse;
import com.im.nettychat.protocol.response.user.GetFriendResponse;
import com.im.nettychat.serialize.Serializer;
import com.im.nettychat.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.HashMap;
import java.util.Map;

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
        packetTypeMap.put(Command.SEND_MESSAGE, MessageRequest.class);
        packetTypeMap.put(Command.CREATE_GROUP, CreateGroupRequest.class);
        packetTypeMap.put(Command.JOIN_GROUP, JoinGroupRequest.class);
        packetTypeMap.put(Command.GET_USER_GROUP, GetUserGroupRequest.class);
        packetTypeMap.put(Command.SEND_GROUP_MESSAGE, SendGroupMessageRequest.class);
        packetTypeMap.put(Command.ADD_FRIEND, AddFriendRequest.class);
        packetTypeMap.put(Command.GET_FRIENDS, GetFriendRequest.class);
        packetTypeMap.put(Command.GET_USER_GROUP_LIST, GetUserGroupListRequest.class);

        test();
        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    // TODO - wait remove, add response for decode
    private void test() {
        packetTypeMap.put(Command.REGISTER_RESPONSE, RegisterResponse.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponse.class);
        packetTypeMap.put(Command.SEND_MESSAGE_RESPONSE, MessageResponse.class);
        packetTypeMap.put(Command.CREATE_GROUP_RESPONSE, CreateGroupResponse.class);
        packetTypeMap.put(Command.JOIN_GROUP_RESPONSE, JoinGroupResponse.class);
        packetTypeMap.put(Command.GET_USER_GROUP_RESPONSE, GetUserGroupResponse.class);
        packetTypeMap.put(Command.SEND_GROUP_MESSAGE_RESPONSE, SendGroupMessageResponse.class);
        packetTypeMap.put(Command.ADD_FRIEND_RESPONSE, AddFriendResponse.class);
        packetTypeMap.put(Command.GET_FRIENDS_RESPONSE, GetFriendResponse.class);
        packetTypeMap.put(Command.GET_USER_GROUP_LIST_RESPONSE, GetUserGroupListResponse.class);
        packetTypeMap.put(Command.OFFLINE_MESSAGE_RESPONSE, OfflineMessageResponse.class);
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
        logger.info("serializeAlgorithm: " + serializeAlgorithm);
        logger.info("command: " + command);
        logger.info("length: " + length);
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
