/*
 * Project: com.im.nettychat.service.impl
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
package com.im.nettychat.service.impl;

import com.im.nettychat.cache.CacheName;
import com.im.nettychat.common.ErrorCode;
import com.im.nettychat.common.OfflineMessageType;
import com.im.nettychat.common.Session;
import com.im.nettychat.model.OfflineMessage;
import com.im.nettychat.model.UserGroup;
import com.im.nettychat.model.key.GroupKey;
import com.im.nettychat.protocol.request.group.CreateGroupRequest;
import com.im.nettychat.protocol.request.group.GetUserGroupListRequest;
import com.im.nettychat.protocol.request.group.GetUserGroupRequest;
import com.im.nettychat.protocol.request.group.JoinGroupRequest;
import com.im.nettychat.protocol.request.group.SendGroupMessageRequest;
import com.im.nettychat.protocol.response.group.CreateGroupResponse;
import com.im.nettychat.protocol.response.group.GetUserGroupListResponse;
import com.im.nettychat.protocol.response.group.GetUserGroupResponse;
import com.im.nettychat.protocol.response.group.JoinGroupResponse;
import com.im.nettychat.protocol.response.group.SendGroupMessageResponse;
import com.im.nettychat.proxy.CglibServiceInterceptor;
import com.im.nettychat.service.BaseService;
import com.im.nettychat.service.GroupService;
import com.im.nettychat.util.BeanUtil;
import com.im.nettychat.util.BooleanUtils;
import com.im.nettychat.util.CollectionUtils;
import com.im.nettychat.util.SessionUtil;
import com.im.nettychat.util.StringUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import static com.im.nettychat.common.AttributeKeys.SESSION_ATTRIBUTE_KEY;
import static com.im.nettychat.model.RedisRepository.redisRepository;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/22 下午10:32
 */
public class GroupServiceImpl extends BaseService implements GroupService {

    private final static String GROUP_USER_ID_SPLIT = ",";

    private final static int GROUP_USER_LIMIT = 50;

    public static final GroupService groupService = (GroupServiceImpl) CglibServiceInterceptor.getCglibProxy(GroupServiceImpl.class);

    @Override
    public void createGroup(ChannelHandlerContext ctx, CreateGroupRequest request) {
        CreateGroupResponse response = new CreateGroupResponse();
        List<Long> groupUserIds = request.getUserIds();
        String filterUserIds = "";
        // 群组名称必填
        if (StringUtils.isEmpty(request.getGroupName())) {
            exceptionResponse(ctx, ErrorCode.GROUP_NAME_REQUIRED, response);
            return;
        }
        Session session = ctx.channel().attr(SESSION_ATTRIBUTE_KEY).get();
        Long userId = session.getUserId();
        // 自己也在群组中
        groupUserIds.add(userId);
        // 去重一次
        groupUserIds = groupUserIds.stream().distinct().collect(Collectors.toList());
        String groupName = request.getGroupName();
        // 拉人创建群组的时候过滤掉无效的用户
        if (CollectionUtils.isNotNullOrEmpty(groupUserIds)) {
            filterUserIds = groupUserIds
                .stream()
                .filter(uid -> {
                    Boolean exits = redisRepository.keyExits(CacheName.USER_INFO, String.valueOf(uid));
                    return BooleanUtils.isTrue(exits);
                })
                .map(v -> String.valueOf(v))
                .reduce((u1, u2) -> u1.concat(GROUP_USER_ID_SPLIT).concat(u2))
                .get();
        }
        Long userGroupId = redisRepository.vIncr(CacheName.USER_GROUP_ID);
        // 记录群组信息
        redisRepository.hMSet(CacheName.USER_GROUP, String.valueOf(userGroupId), getGroupInfo(groupName, filterUserIds, userId));
        Arrays.asList(filterUserIds.split(GROUP_USER_ID_SPLIT)).forEach(uID -> {
            // 给每个用户记录他有那些一个群组
            redisRepository.sAdd(CacheName.USER_ID_USER_GROUP, String.valueOf(uID), String.valueOf(userGroupId));
        });
        response.setGroupId(userGroupId);
        ctx.writeAndFlush(response);
    }

    @Override
    public void joinGroup(ChannelHandlerContext ctx, JoinGroupRequest msg) {
        JoinGroupResponse response = new JoinGroupResponse();
        boolean exits = redisRepository.keyExits(CacheName.USER_GROUP, String.valueOf(msg.getGroupId()));
        if (!exits) {
            exceptionResponse(ctx, ErrorCode.GROUP_NOT_FOUND, response);
            return;
        }
        Long userId = ctx.channel().attr(SESSION_ATTRIBUTE_KEY).get().getUserId();
        boolean existGroup = redisRepository.sismember(CacheName.USER_ID_USER_GROUP, String.valueOf(userId), String.valueOf(msg.getGroupId()));
        if (existGroup) {
            exceptionResponse(ctx, ErrorCode.GROUP_USER_ALREADY_EXIST, response);
            return;
        }
        UserGroup userGroup = getUserGroup(msg.getGroupId());
        String groupUserIds = userGroup.getUserIdsStr();
        if (StringUtils.isEmpty(groupUserIds)) {
            groupUserIds = String.valueOf(userId);
        } else {
            groupUserIds = groupUserIds.concat(GROUP_USER_ID_SPLIT).concat(String.valueOf(userId));
        }
        // 群组人员到达上限
        if (groupUserIds.split(GROUP_USER_ID_SPLIT).length > GROUP_USER_LIMIT) {
            exceptionResponse(ctx, ErrorCode.GROUP_USER_UPPER_LIMIT, response);
            return;
        }
        // 维护群组信息增加一个成员
        redisRepository.hSet(CacheName.USER_GROUP, String.valueOf(msg.getGroupId()), GroupKey.USER_IDS, groupUserIds);
        // 加入群组的人新增一个群组
        redisRepository.sAdd(CacheName.USER_ID_USER_GROUP, String.valueOf(userId), String.valueOf(msg.getGroupId()));
        response.setGroupName(userGroup.getGroupName());
        response.setGroupId(msg.getGroupId());
        response.setIcon(userGroup.getIcon());
        response.setUserIds(Arrays.asList(groupUserIds.split(GROUP_USER_ID_SPLIT)).stream().map(v -> Long.valueOf(v)).collect(Collectors.toList()));
        ctx.writeAndFlush(response);
    }

    @Override
    public void getUserGroup(ChannelHandlerContext ctx, GetUserGroupRequest msg) {
        GetUserGroupResponse response = new GetUserGroupResponse();
        boolean exits = redisRepository.keyExits(CacheName.USER_GROUP, String.valueOf(msg.getGroupId()));
        if (!exits) {
            exceptionResponse(ctx, ErrorCode.GROUP_NOT_FOUND, response);
            return;
        }
        UserGroup userGroup = getUserGroup(msg.getGroupId());
        BeanUtil.copyProperties(userGroup, response);
        ctx.writeAndFlush(response);
    }

    @Override
    public void sendGroupMessage(ChannelHandlerContext ctx, SendGroupMessageRequest msg) {
        SendGroupMessageResponse response = new SendGroupMessageResponse();
        boolean exits = redisRepository.keyExits(CacheName.USER_GROUP, String.valueOf(msg.getGroupId()));
        // 没有找到群组
        if (!exits) {
            exceptionResponse(ctx, ErrorCode.GROUP_NOT_FOUND, response);
            return;
        }
        Long userId = ctx.channel().attr(SESSION_ATTRIBUTE_KEY).get().getUserId();
        boolean existGroup = redisRepository.sismember(CacheName.USER_ID_USER_GROUP, String.valueOf(userId), String.valueOf(msg.getGroupId()));
        // 用户不再群组中
        if (!existGroup) {
            exceptionResponse(ctx, ErrorCode.GROUP_NOT_THIS_USER, response);
            return;
        }
        UserGroup userGroup = getUserGroup(msg.getGroupId());
        List<Long> userIds = userGroup.getUserIds();
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
        String message = msg.getMessage();
        List<Long> offlineUserIds = new ArrayList<>();
        for(Long uid: userIds) {
            if (uid.equals(userId)) {
                continue;
            }
            Channel channel = SessionUtil.getChannel(uid);
            // 用户不在线存储到它的用户离线消息中
            if (channel == null) {
                offlineUserIds.add(uid);
                continue;
            }
            channelGroup.add(channel);
        }
        response.setSendUserId(userId);
        response.setMessage(message);
        // 此处后立即返回给客户端消息
        channelGroup.writeAndFlush(response);

        // 存储离线消息
        for(Long offlineUserId: offlineUserIds) {
            // 存储到它的用户离线消息中
            OfflineMessage offlineMessage = new OfflineMessage();
            offlineMessage.setMessage(message);
            offlineMessage.setMessageType(OfflineMessageType.GROUP);
            offlineMessage.setGroupId(msg.getGroupId());
            offlineMessage.setUserId(userId);
            redisRepository
                    .lPush(CacheName.OFFLINE_MESSAGE, String.valueOf(offlineUserId), offlineMessage);
        }
    }

    @Override
    public void getUserGroupList(ChannelHandlerContext ctx, GetUserGroupListRequest msg) {
        GetUserGroupListResponse response = new GetUserGroupListResponse();
        List<UserGroup> userGroups = new ArrayList<>();
        Long userId = ctx.channel().attr(SESSION_ATTRIBUTE_KEY).get().getUserId();
        Set<String> groupIdSet = redisRepository.sGet(CacheName.USER_ID_USER_GROUP, String.valueOf(userId));
        // 已经解散的群组
        Set<String> cancelGroupIds = new HashSet<>();
        for(String groupId: groupIdSet) {
            UserGroup userGroup = getUserGroup(Long.valueOf(groupId));
            if (userGroup == null) {
                cancelGroupIds.add(groupId);
            }
            userGroup.setUserIdsStr(null);
            userGroups.add(userGroup);
        }
        // 删除已经解散或者无效的群组
        String[] cancelGroupIdArr = new String[cancelGroupIds.size()];
        if (CollectionUtils.isNotNullOrEmpty(cancelGroupIds)) {
            deleteUserGroup(userId, cancelGroupIds.toArray(cancelGroupIdArr));
        }
        response.setUserGroups(userGroups);
        ctx.writeAndFlush(response);
    }

    /**
     *  删除用户某个群组
     */
    private void deleteUserGroup(Long userId, String... groupId) {
        redisRepository.sRemove(CacheName.USER_ID_USER_GROUP, String.valueOf(userId), groupId);
    }

    private UserGroup getUserGroup(Long groupId) {
        List<String> groupInfo = redisRepository.hMGet(CacheName.USER_GROUP,
            String.valueOf(groupId),
            GroupKey.GROUP_NAME,
            GroupKey.OWNER,
            GroupKey.USER_IDS,
            GroupKey.ICON,
            GroupKey.DESC);
        if (CollectionUtils.isNullOrEmpty(groupInfo)) {
            return null;
        }
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupId(groupId);
        userGroup.setGroupName(groupInfo.get(0));
        userGroup.setOwner(Long.valueOf(groupInfo.get(1)));
        userGroup.setUserIdsStr(groupInfo.get(2));
        if (StringUtils.isNotEmpty(userGroup.getUserIdsStr())) {
            List<Long> userIds = Arrays.asList(groupInfo.get(2).split(GROUP_USER_ID_SPLIT)).stream().map(v -> Long.valueOf(v)).collect(Collectors.toList());
            userGroup.setUserIds(userIds);
        }
        userGroup.setIcon(groupInfo.get(3));
        userGroup.setDesc(groupInfo.get(4));
        return userGroup;
    }

    private Map<String, String> getGroupInfo(String groupName, String filterUserIds, Long owner) {
        Map<String, String> groupInfo = new HashMap<>();
        groupInfo.put(GroupKey.GROUP_NAME, groupName);
        groupInfo.put(GroupKey.USER_IDS, filterUserIds);
        groupInfo.put(GroupKey.OWNER, String.valueOf(owner));
        return groupInfo;
    }
}
