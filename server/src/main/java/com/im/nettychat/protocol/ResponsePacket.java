/*
 * Project: com.im.nettychat.protocol
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
package com.im.nettychat.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/20 下午9:12
 */
@Data
public abstract class ResponsePacket extends Packet {

    protected boolean error;

    protected String errorInfo;
}
