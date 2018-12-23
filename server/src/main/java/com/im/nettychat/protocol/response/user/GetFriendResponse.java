/*
 * Project: com.im.nettychat.protocol.response.user
 * 
 * File Created at 2018/12/23
 * 
 * Copyright 2018 CMCC Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * ZYHY Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license.
 */
package com.im.nettychat.protocol.response.user;

import com.im.nettychat.common.Command;
import com.im.nettychat.model.User;
import lombok.Data;

import java.util.List;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/23 下午9:16
 */
@Data
public class GetFriendResponse extends UserResponse {

    private List<User> friends;

    @Override
    public Byte getCommand() {
        return Command.GET_FRIENDS_RESPONSE;
    }
}
