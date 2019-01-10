package com.im.nettychat.service.impl;

import com.im.nettychat.cache.CacheName;
import com.im.nettychat.common.ErrorCode;
import com.im.nettychat.common.GenerateID;
import com.im.nettychat.common.OfflineMessageType;
import com.im.nettychat.common.Session;
import com.im.nettychat.domain.User;
import com.im.nettychat.model.OfflineMessage;
import com.im.nettychat.protocol.dto.UserDTO;
import com.im.nettychat.protocol.request.MessageRequest;
import com.im.nettychat.protocol.request.user.AddFriendRequest;
import com.im.nettychat.protocol.request.user.GetFriendRequest;
import com.im.nettychat.protocol.response.MessageResponse;
import com.im.nettychat.protocol.response.offline.OfflineMessageResponse;
import com.im.nettychat.protocol.response.user.AddFriendResponse;
import com.im.nettychat.protocol.response.user.GetFriendResponse;
import com.im.nettychat.proxy.CglibServiceInterceptor;
import com.im.nettychat.protocol.request.LoginRequest;
import com.im.nettychat.protocol.request.RegisterRequest;
import com.im.nettychat.protocol.response.LoginResponse;
import com.im.nettychat.protocol.response.RegisterResponse;
import com.im.nettychat.service.BaseService;
import com.im.nettychat.service.UserService;
import com.im.nettychat.util.CollectionUtils;
import com.im.nettychat.util.LockUtil;
import com.im.nettychat.util.SessionUtil;
import com.im.nettychat.util.StringUtils;
import com.im.nettychat.util.Util;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import static com.im.nettychat.common.AttributeKeys.SESSION_ATTRIBUTE_KEY;
import static com.im.nettychat.model.RedisRepository.redisRepository;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/21 下午10:56
 */
public class UserServiceImpl3 {
/*

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(UserServiceImpl.class);

    public static final UserService userService = (UserServiceImpl) CglibServiceInterceptor.getCglibProxy(UserServiceImpl.class);

    public void login(ChannelHandlerContext ctx, LoginRequest msg) {
        LoginResponse response = new LoginResponse();
        String userId = redisRepository.hGet(CacheName.USERNAME_ID, msg.getUsername());
        // 用户不存在
        if (userId == null || userId.trim().length() == 0) {
            exceptionResponse(ctx, ErrorCode.USER_NOT_FOUND, response);
            return;
        }
        User user = redisRepository.vGetObject(CacheName.USER_INFO, String.valueOf(userId), User.class);
        if (!user.isValidPassword(msg.getPassword())) {
            exceptionResponse(ctx, ErrorCode.PASSWORD_ERROR, response);
            return;
        }
        response.setUserId(user.getId());
        response.setName(user.getName());
        response.setDesc(user.getDesc());
        response.setIcon(user.getIcon());

        bindSession(user, ctx.channel());
        ctx.writeAndFlush(response);

        // 登录成功后返回所有未读的离线消息
        sendOfflineMessageAndRemove(ctx, userId);
    }

    public void register(ChannelHandlerContext ctx, RegisterRequest msg) {
        RegisterResponse response = new RegisterResponse();
        if (StringUtils.isEmpty(msg.getUsername()) || StringUtils.isEmpty(msg.getPassword())) {
            exceptionResponse(ctx, ErrorCode.NEED_USERNAME_PASSWORD, response);
            return;
        }
        LockUtil.lock(CacheName.REGISTER_LOCK);
        try {
            // 检测用户是否已经被注册了
            String existUserId = redisRepository.hGet(CacheName.USERNAME_ID, msg.getUsername());
            if (existUserId != null) {
                exceptionResponse(ctx, ErrorCode.USER_ALREADY_EXIST, response);
                return;
            }
            Long userId = GenerateID.generateUserID();
            User user = new User();
            user.setId(userId);
            user.setName(msg.getName());
            user.setUsername(msg.getUsername());
            user.setPassword(Util.hashPasswordAddingSalt(msg.getPassword()));
            redisRepository.vSetObject(CacheName.USER_INFO, String.valueOf(userId), user);
            redisRepository.hSet(CacheName.USERNAME_ID, msg.getUsername(), String.valueOf(userId));
            response.setUserId(userId);
            response.setName(user.getName());

            bindSession(user, ctx.channel());
            ctx.writeAndFlush(response);
        } finally {
            LockUtil.unLock(CacheName.REGISTER_LOCK);
        }
    }

    @Override
    public void addFriend(ChannelHandlerContext ctx, AddFriendRequest request) {
        AddFriendResponse response = new AddFriendResponse();
        Long friendUserId = request.getFriendUserId();
        boolean exits = redisRepository.keyExits(CacheName.USER_INFO, String.valueOf(friendUserId));
        Long userId = ctx.channel().attr(SESSION_ATTRIBUTE_KEY).get().getUserId();
        if(!exits) {
            exceptionResponse(ctx, ErrorCode.USER_NOT_FOUND, response);
            return;
        }
        redisRepository.sAdd(CacheName.USER_FRIEND, String.valueOf(userId), String.valueOf(friendUserId));
        ctx.writeAndFlush(response);
    }

    @Override
    public void getFriends(ChannelHandlerContext ctx, GetFriendRequest msg) {
        GetFriendResponse response = new GetFriendResponse();
        Long userId = ctx.channel().attr(SESSION_ATTRIBUTE_KEY).get().getUserId();
        Set<String> friendUserIds = redisRepository.sGet(CacheName.USER_FRIEND, String.valueOf(userId));
        List<UserDTO> friends = new ArrayList<>();
        if (CollectionUtils.isNotNullOrEmpty(friendUserIds)) {
            for (String friendId: friendUserIds) {
                User user = redisRepository.vGetObject(CacheName.USER_INFO, String.valueOf(friendId), User.class);
                if (user == null) {
                    logger.warn("friend userId is not exits: " + friendId);
                    continue;
                }
                UserDTO userDTO = new UserDTO();
                userDTO.setId(user.getId());
                userDTO.setUsername(user.getUsername());
                userDTO.setName(user.getName());
                userDTO.setIcon(user.getIcon());
                userDTO.setDesc(user.getDesc());
                friends.add(userDTO);
            }
        }
        response.setFriends(friends);
        ctx.writeAndFlush(response);
    }

    @Override
    public void sendMessage(ChannelHandlerContext ctx, MessageRequest request) {
        MessageResponse response = new MessageResponse();

        String message = request.getMessage();
        Long toUserId = request.getToUserId();
        boolean exits = redisRepository.keyExits(CacheName.USER_INFO, String.valueOf(toUserId));
        if(!exits) {
            exceptionResponse(ctx, ErrorCode.USER_NOT_FOUND, response);
            return;
        }
        Long fromUserId = ctx.channel().attr(SESSION_ATTRIBUTE_KEY).get().getUserId();
        Channel toChannel = SessionUtil.getChannel(toUserId);
        if (fromUserId.equals(toUserId)) {
            return;
        }
        // 发送离线消息
        if (toChannel == null) {
            // 存储到它的用户离线消息中
            OfflineMessage offlineMessage = new OfflineMessage();
            offlineMessage.setMessage(message);
            offlineMessage.setMessageType(OfflineMessageType.USER);
            offlineMessage.setUserId(toUserId);
            redisRepository
                    .lPush(CacheName.OFFLINE_MESSAGE, String.valueOf(toUserId), offlineMessage);
            return;
        }
        response.setMessage(message);
        response.setFromUserId(fromUserId);
        toChannel.writeAndFlush(response);
    }

    private void sendOfflineMessageAndRemove(ChannelHandlerContext ctx, String userId) {
        OfflineMessageResponse offlineMessageResponse = new OfflineMessageResponse();
        List<OfflineMessage> offlineMessages = redisRepository.lRangeObject(CacheName.OFFLINE_MESSAGE, userId, 0, -1, OfflineMessage.class);
        offlineMessageResponse.setMessages(offlineMessages);
        ctx.writeAndFlush(offlineMessageResponse).addListener(future -> {
            // 消息推送成功后删除
            redisRepository.removeKey(CacheName.OFFLINE_MESSAGE, userId);
        });
    }

    private void bindSession(User user, Channel channel) {
        Session session = new Session();
        session.setUserId(user.getId());
        session.setUsername(user.getUsername());
        SessionUtil.bindSession(session, channel);
    }
*/

}
