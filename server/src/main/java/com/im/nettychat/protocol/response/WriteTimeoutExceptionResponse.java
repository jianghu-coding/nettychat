/*
 * Project: com.im.nettychat.protocol.response
 * 
 * File Created at 2018/12/26
 * 
 * Copyright 2018 CMCC Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * ZYHY Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license.
 */
package com.im.nettychat.protocol.response;

import com.im.nettychat.common.Command;
import com.im.nettychat.protocol.PacketResponse;
import lombok.Data;

/**
 * @author hejianglong
 * @date 2018/12/26 下午9:37
 * @email hejlong@163.com
 * @Desc
 */
@Data
public class WriteTimeoutExceptionResponse extends PacketResponse {

    private String message;

    @Override
    public Byte getCommand() {
        return Command.WRITE_TIME_OUT;
    }
}
