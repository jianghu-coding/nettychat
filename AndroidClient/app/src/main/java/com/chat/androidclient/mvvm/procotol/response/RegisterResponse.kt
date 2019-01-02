/*
 * Project: com.im.nettychat.protocol.response
 * 
 * File Created at 2018/12/21
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
 * @date 2018/12/21 下午6:35
 */

class RegisterResponse : PacketResponse() {

     var userId: Long? = null

     var name: String? = null

     var icon: String? = null

     var desc: String? = null

    override val command: Byte
        get() = Command.REGISTER_RESPONSE
}

