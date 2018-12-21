/*
 * Project: com.im.nettychat.protocol.request
 * 
 * File Created at 2018/12/20
 * 
 * Copyright 2018 CMCC Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * ZYHY Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license.
 */
package com.im.nettychat.protocol.request;

import com.im.nettychat.common.Command;
import com.im.nettychat.protocol.RequestPacket;
import lombok.Data;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/20 下午9:33
 */
@Data
public class RegisterRequest extends RequestPacket {

    private String name;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return Command.REGISTER;
    }
}
