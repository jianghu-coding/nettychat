package com.im.nettychat.service.impl;

import com.im.nettychat.cache.CacheName;
import com.im.nettychat.common.ErrorCode;
import com.im.nettychat.common.GenerateID;
import com.im.nettychat.common.Session;
import com.im.nettychat.config.ErrorConfig;
import com.im.nettychat.proxy.CglibServiceInterceptor;
import com.im.nettychat.model.User;
import com.im.nettychat.protocol.request.LoginRequest;
import com.im.nettychat.protocol.request.RegisterRequest;
import com.im.nettychat.protocol.response.LoginResponse;
import com.im.nettychat.protocol.response.RegisterResponse;
import com.im.nettychat.service.UserService;
import com.im.nettychat.util.LockUtil;
import com.im.nettychat.util.SessionUtil;
import com.im.nettychat.util.StringUtils;
import com.im.nettychat.util.Util;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import static com.im.nettychat.common.ErrorCode.NEED_USERNAME_PASSWORD;
import static com.im.nettychat.model.RedisRepository.redisRepository;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/21 下午10:56
 */
public class UserServiceImpl implements UserService {

    public static final UserService userService = (UserServiceImpl) CglibServiceInterceptor.getCglibProxy(UserServiceImpl.class);

    public void login(ChannelHandlerContext ctx, LoginRequest msg) {
        LoginResponse response = new LoginResponse();
        String userId = redisRepository.hGet(CacheName.USERNAME_ID, msg.getUsername());
        // 用户不存在
        if (userId == null || userId.trim().length() == 0) {
            response.setError(true);
            response.setErrorInfo(ErrorConfig.getError(ErrorCode.USER_NOT_FOUND));
            ctx.writeAndFlush(response);
            return;
        }
        User user = redisRepository.vGetObject(CacheName.USER_INFO, String.valueOf(userId), User.class);
        if (!user.isValidPassword(msg.getPassword())) {
            response.setError(true);
            response.setErrorInfo(ErrorConfig.getError(ErrorCode.PASSWORD_ERROR));
            ctx.writeAndFlush(response);
            return;
        }
        response.setUserId(user.getId());
        response.setName(user.getName());
        response.setDesc(user.getDesc());
        response.setIcon(user.getIcon());

        bindSession(user, ctx.channel());
        ctx.writeAndFlush(response);
    }

    public void register(ChannelHandlerContext ctx, RegisterRequest msg) {
        RegisterResponse response = new RegisterResponse();
        if (StringUtils.isEmpty(msg.getUsername()) || StringUtils.isEmpty(msg.getPassword())) {
            response.setError(true);
            response.setErrorInfo(NEED_USERNAME_PASSWORD);
            ctx.writeAndFlush(response);
            return;
        }
        LockUtil.lock(CacheName.REGISTER_LOCK);
        try {
            // 检测用户是否已经被注册了
            String existUserId = redisRepository.hGet(CacheName.USERNAME_ID, msg.getUsername());
            if (existUserId != null) {
                response.setError(true);
                response.setErrorInfo(ErrorConfig.getError(ErrorCode.USER_ALREADY_EXIST));
                ctx.writeAndFlush(response);
                ctx.fireChannelReadComplete();
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

    private void bindSession(User user, Channel channel) {
        Session session = new Session();
        session.setUserId(user.getId());
        session.setUsername(user.getUsername());
        SessionUtil.bindSession(session, channel);
    }

}
