/*
 * Project: com.im.nettychat.protocol.response
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
package com.chat.androidclient.mvvm.procotol.response

import com.chat.androidclient.mvvm.model.Command
import com.chat.androidclient.mvvm.model.PacketResponse

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/20 下午8:59
 */

class LoginResponse : PacketResponse() {
    override val command: Byte
        get() = Command.LOGIN_RESPONSE
    
    private val userId: Long? = null

    private val name: String? = null

    private val icon: String? = null

    private val desc: String? = null

  
}
