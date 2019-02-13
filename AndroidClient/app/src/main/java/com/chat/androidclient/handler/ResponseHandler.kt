package com.chat.androidclient.handler

import com.blankj.utilcode.util.LogUtils
import com.chat.androidclient.event.*
import com.chat.androidclient.mvvm.model.Command
import com.chat.androidclient.mvvm.model.PacketResponse
import com.chat.androidclient.mvvm.procotol.response.SendGroupMessageResponse
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import org.greenrobot.eventbus.EventBus

/**
 * Created by lps on 2018/12/25 13:27.
 */
class ResponseHandler : SimpleChannelInboundHandler<PacketResponse>() {
    
    override fun channelRead0(ctx: ChannelHandlerContext, msg: PacketResponse) {
        LogUtils.e(msg.toString())
        when (msg.command) {
            Command.LOGIN_RESPONSE -> {
                EventBus.getDefault().post(LoginResponseEvent(msg))
            }
            Command.REGISTER_RESPONSE -> {
                EventBus.getDefault().post(SignUpResponseEvent(msg))
            }
            Command.SEND_MESSAGE_RESPONSE -> {
                EventBus.getDefault().post(MessageEvent(msg))
            }
            Command.GET_FRIENDS_RESPONSE -> {
                EventBus.getDefault().post(FriendResponseEvent(msg))
            }
            Command.SEARCH_FRIEND_RESPONSE -> {
                EventBus.getDefault().post(SearchFriendResponseEvent(msg))
            }
            Command.ADD_FRIEND_RESPONSE -> {
                EventBus.getDefault().post(AddFriendResponseEvent(msg))
            }
            Command.CREATE_GROUP_RESPONSE->{
                EventBus.getDefault().post(CreateGroupResponseEvent(msg))
    
            }
            Command.GET_USER_GROUP_LIST_RESPONSE->{
                EventBus.getDefault().post(GetGroupListResponseEvent(msg))
    
            }
            Command.SEARCH_GROUP_RESPONSE->{
                EventBus.getDefault().post(SearchGroupResponseEvent(msg))
    
            } Command.GET_USER_GROUP_RESPONSE->{
                EventBus.getDefault().post(GetUserGroupResponseEvent(msg))
            }Command.JOIN_GROUP_RESPONSE->{
                EventBus.getDefault().post(JoinGroupResponseEvent(msg))
            }
            Command.SEND_GROUP_MESSAGE_RESPONSE->{
                EventBus.getDefault().post(ReciveGroupMsgResponseEvent(msg))
            }
            
        }
    }
    
}