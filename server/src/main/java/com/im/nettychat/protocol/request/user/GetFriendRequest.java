/*
 * Project: com.im.nettychat.protocol.request.user
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
package com.im.nettychat.protocol.request.user;

import com.im.nettychat.common.Command;
import lombok.Data;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/23 下午9:14
 */
@Data
public class GetFriendRequest extends UserRequest {

    @Override
    public Byte getCommand() {
        return Command.GET_FRIENDS;
    }
}
