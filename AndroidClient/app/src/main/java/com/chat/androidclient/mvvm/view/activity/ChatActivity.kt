package com.chat.androidclient.mvvm.view.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import com.chat.androidclient.R
import com.chat.androidclient.databinding.ActivityConversationBinding
import com.chat.androidclient.mvvm.viewmodel.ChatVM

class ChatActivity : BaseActivity<ActivityConversationBinding, ChatVM>() {
    companion object {
         val ID = "id"
        @JvmStatic
        fun startActivity(context: Context, chatId: Long) {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(ID, chatId)
            context.startActivity(intent)
        }
    }
    
    override fun getViewModel() = ChatVM(this)
    
    override fun getLayoutRes() = R.layout.activity_conversation
    
    override fun init() {
        mDataBinding.vm = mVM
        mDataBinding.ivCall.setColorFilter(Color.WHITE)
        mDataBinding.ivInfo.setColorFilter(Color.WHITE)
    }
    
    fun canClickSendBtn(canSend: Boolean) {
        mDataBinding.btnSendMsg.isEnabled = canSend
        mDataBinding.btnSendMsg.isSelected = canSend
    }
    
    override fun onClick(v: View) {
    when(v.id){
        R.id.btn_send_msg->{
            mVM.sendMsg(mDataBinding.msgInput.text.toString())
        }
    }
    }
    
    fun clearInput() {
        mDataBinding.msgInput.setText("")
    }
}
