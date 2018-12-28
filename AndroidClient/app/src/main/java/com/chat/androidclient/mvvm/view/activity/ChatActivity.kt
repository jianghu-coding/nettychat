package com.chat.androidclient.mvvm.view.activity

import android.graphics.Color
import com.chat.androidclient.R
import com.chat.androidclient.databinding.ActivityConversationBinding
import com.chat.androidclient.mvvm.viewmodel.ChatVM

class ChatActivity : BaseActivity<ActivityConversationBinding,ChatVM>() {
    override fun getViewModel()= ChatVM (this)
    
    override fun getLayoutRes()=R.layout.activity_conversation
    
    override fun init() {
        mDataBinding.vm=mVM
        mDataBinding.ivCall.setColorFilter(Color.WHITE)
        mDataBinding.ivInfo.setColorFilter(Color.WHITE)
    }
    
    fun canClickSendBtn(canSend: Boolean) {
        mDataBinding.btnSendMsg.isEnabled=canSend
        mDataBinding.btnSendMsg.isSelected=canSend
    }
}
