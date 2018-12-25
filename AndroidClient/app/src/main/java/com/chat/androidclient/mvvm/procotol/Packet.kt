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
package com.chat.androidclient.mvvm.procotol

import com.alibaba.fastjson.annotation.JSONField
import java.io.Serializable


abstract class Packet:Serializable {
    /**
     * 协议版本
     */
    @JSONField(deserialize = false, serialize = false)
     val version = 1


    @get:JSONField(serialize = false)
    abstract val command: Byte
}
