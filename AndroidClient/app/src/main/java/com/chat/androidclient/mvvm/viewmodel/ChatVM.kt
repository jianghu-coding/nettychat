package com.chat.androidclient.mvvm.viewmodel

import android.text.Editable
import android.text.TextWatcher
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.SpanUtils
import com.chat.androidclient.event.RefreshConversationEvent
import com.chat.androidclient.greendao.DaoMaster
import com.chat.androidclient.im.ChatIM
import com.chat.androidclient.mvvm.model.Constant
import com.chat.androidclient.mvvm.model.Conversation
import com.chat.androidclient.mvvm.procotol.request.SendMessageRequest
import com.chat.androidclient.mvvm.procotol.response.MessageResponse
import com.chat.androidclient.mvvm.view.activity.ChatActivity
import org.greenrobot.eventbus.EventBus

/**
 * Created by lps on 2018/12/28 14:00.
 */
class ChatVM(var view: ChatActivity) : BaseViewModel() {
    var id:Long = view.intent.getLongExtra(ChatActivity.ID,-1)
    val devSession = DaoMaster.newDevSession(view, Constant.DBNAME)
    val msgDao = devSession.messageResponseDao
    val conversationDao = devSession.conversationDao
    
    fun sendMsg(msg:String){
        ChatIM.instance.cmd(SendMessageRequest(id,msg))
//清空输入框
        view.clearInput()
//        todo insert db
        val message = MessageResponse()
        message.fromUserId= getMyId()
        message.message=msg
        message.toUserId=id
        msgDao.insertOrReplace(message)
//        todo refresh list
        // 最近会话列表DB 刷新这个好友
        val conversation = Conversation()
        conversation.fromId=getMyId()
        conversation.lastcontent=msg
        conversation.time=System.currentTimeMillis().toString()
        conversationDao.insertOrReplace(conversation)
        //通知最近会话列表更新
        EventBus.getDefault().post(RefreshConversationEvent())
    }
    
    private fun getMyId() = SPUtils.getInstance().getLong(Constant.id)
    fun getInputWatcher():TextWatcher=object :TextWatcher{
        override fun afterTextChanged(s: Editable) {
            if (s.length>0){
                view.canClickSendBtn(true)
            }else{
                view.canClickSendBtn(false)
    
            }
        }
    
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
    
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }
}