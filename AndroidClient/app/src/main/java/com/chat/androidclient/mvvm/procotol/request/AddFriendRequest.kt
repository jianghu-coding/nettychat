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
package com.chat.androidclient.mvvm.procotol.request


import com.chat.androidclient.mvvm.model.Command
import com.chat.androidclient.mvvm.procotol.Packet

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/23 下午8:43
 */

class AddFriendRequest(var friendUserId: Long?) : Packet() {
    override val command: Byte
        get() = Command.ADD_FRIEND
    
    override fun toString(): String {
        return "AddFriendRequest(friendUserId=$friendUserId)"
    }
    
    
}
