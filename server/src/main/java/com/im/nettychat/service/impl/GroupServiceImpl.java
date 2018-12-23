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
import com.im.nettychat.common.Session;
import com.im.nettychat.model.key.GroupKey;
import com.im.nettychat.protocol.request.CreateGroupRequest;
import com.im.nettychat.protocol.response.CreateGroupResponse;
import com.im.nettychat.proxy.CglibServiceInterceptor;
import com.im.nettychat.service.BaseService;
import com.im.nettychat.service.GroupService;
import com.im.nettychat.util.BooleanUtils;
import com.im.nettychat.util.CollectionUtils;
import com.im.nettychat.util.StringUtils;
import io.netty.channel.ChannelHandlerContext;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.im.nettychat.common.AttributeKeys.SESSION_ATTRIBUTE_KEY;
import static com.im.nettychat.model.RedisRepository.redisRepository;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/22 下午10:32
 */
public class GroupServiceImpl extends BaseService implements GroupService {

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
        String groupName = String.valueOf(userId).concat("@").concat(request.getGroupName());
        // 拉人创建群组的时候过滤掉无效的用户
        if (CollectionUtils.isNotNullOrEmpty(groupUserIds)) {
            filterUserIds = groupUserIds
                .stream()
                .filter(uid -> {
                    Boolean exits = redisRepository.vExits(CacheName.USER_INFO, String.valueOf(uid));
                    return BooleanUtils.isTrue(exits);
                })
                .map(v -> String.valueOf(v))
                .reduce((u1, u2) -> u1.concat(",").concat(u2))
                .get();
        }
        Long userGroupId = redisRepository.vIncr(CacheName.USER_GROUP_ID);
        // 记录群组信息
        redisRepository.hMSet(CacheName.USER_GROUP, String.valueOf(userGroupId), getGroupInfo(groupName, filterUserIds, userId));
        Arrays.asList(filterUserIds.split(",")).forEach(uID -> {
            // 给每个用户记录他有那些一个群组
            redisRepository.hSet(CacheName.USER_ID_USER_GROUP, String.valueOf(uID), String.valueOf(userGroupId));
        });
        response.setGroupId(userGroupId);
        ctx.writeAndFlush(response);
    }

    private Map<String, String> getGroupInfo(String groupName, String filterUserIds, Long owner) {
        Map<String, String> groupInfo = new HashMap<>();
        groupInfo.put(GroupKey.GROUP_NAME, groupName);
        groupInfo.put(GroupKey.USER_IDS, filterUserIds);
        groupInfo.put(GroupKey.OWNER, String.valueOf(owner));
        return groupInfo;
    }
}
