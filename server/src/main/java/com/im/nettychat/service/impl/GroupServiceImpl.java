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
import com.im.nettychat.protocol.request.CreateGroupRequest;
import com.im.nettychat.protocol.response.CreateGroupResponse;
import com.im.nettychat.proxy.CglibServiceInterceptor;
import com.im.nettychat.service.GroupService;
import com.im.nettychat.util.BooleanUtils;
import com.im.nettychat.util.CollectionUtils;
import com.im.nettychat.util.StringUtils;
import io.netty.channel.ChannelHandlerContext;
import java.util.List;
import static com.im.nettychat.common.AttributeKeys.SESSION_ATTRIBUTE_KEY;
import static com.im.nettychat.model.RedisRepository.redisRepository;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/22 下午10:32
 */
public class GroupServiceImpl implements GroupService {

    public static final GroupService groupService = (GroupServiceImpl) CglibServiceInterceptor.getCglibProxy(GroupServiceImpl.class);

    @Override
    public void createGroup(ChannelHandlerContext ctx, CreateGroupRequest request) {
        CreateGroupResponse response = new CreateGroupResponse();
        List<Long> groupUserIds = request.getUserIds();
        String filterUserIds = "";
        if (StringUtils.isEmpty(request.getGroupName())) {
            response.setError(true);
            response.setErrorInfo(ErrorCode.GROUP_NAME_REQUIRED);
            ctx.writeAndFlush(response);
            return;
        }
        // 拉人创建群组的时候过滤掉无效的用户
        if (CollectionUtils.isNotNullOrEmpty(groupUserIds)) {
            filterUserIds = groupUserIds
                .stream()
                .filter(uid -> {
                    Boolean exits = redisRepository.vExits(CacheName.USER_INFO, String.valueOf(uid));
                    return BooleanUtils.isNotTrue(exits);
                })
                .map(v -> String.valueOf(v))
                .reduce((u1, u2) -> u1.concat(",").concat(u2))
                .get();
        }
        Session session = ctx.channel().attr(SESSION_ATTRIBUTE_KEY).get();
        String groupName = String.valueOf(session.getUserId()).concat("@").concat(request.getGroupName());
        // 记录群组信息
        redisRepository.hSet(CacheName.USER_GROUP, groupName, filterUserIds);
        // TODO - 给每个用户创建一个群组
    }
}
