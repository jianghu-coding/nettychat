package com.chat.androidclient.event

import com.chat.androidclient.mvvm.model.PacketResponse

/**
 * Created by lps on 2018/12/27 10:57.
 * 根据群组id 查询 群组详情的 event
 */
class GetUserGroupResponseEvent(var msg: PacketResponse)  {
}