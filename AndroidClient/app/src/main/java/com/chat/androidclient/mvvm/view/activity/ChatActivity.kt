package com.chat.androidclient.mvvm.view.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chat.androidclient.R
import com.chat.androidclient.adapter.ConversationAdapter
import com.chat.androidclient.databinding.ActivityConversationBinding
import com.chat.androidclient.mvvm.model.TYPE
import com.chat.androidclient.mvvm.procotol.response.MessageResponse
import com.chat.androidclient.mvvm.viewmodel.ChatVM

/**
 * @author lps
 * 聊天界面
 */
class ChatActivity : BaseActivity<ActivityConversationBinding, ChatVM>() {
    lateinit var adapter: ConversationAdapter
    
    companion object {
        val ID = "id"
        val MSG = "msg"
        val TYPE = "type"
        @JvmStatic
        fun startActivity(context: Context, chatId: Long,type:TYPE) {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(ID, chatId)
            intent.putExtra(TYPE, type)
            context.startActivity(intent)
        }
        
        @JvmStatic
        fun startActivity(context: Context, chatId: Long, msg: String) {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(ID, chatId)
            intent.putExtra(MSG, msg)
            intent.putExtra(TYPE, com.chat.androidclient.mvvm.model.TYPE.PERSON)
            context.startActivity(intent)
        }
        
    }
    
    override fun getViewModel() = ChatVM(this)
    
    override fun getLayoutRes() = R.layout.activity_conversation
    
    
    override fun init() {
        mDataBinding.vm = mVM
        initTitle()
        initRecyclerView()
        mVM.init()
    }
    
    private fun initTitle() {
        mDataBinding.ivCall.setColorFilter(Color.WHITE)
        mDataBinding.ivInfo.setColorFilter(Color.WHITE)
    }
    fun setConversationTitle(info:String){
        mDataBinding.converUser.text=info
    }
    
    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        mDataBinding.messageRecyclerView.layoutManager = layoutManager
        adapter = ConversationAdapter(this)
        mDataBinding.messageRecyclerView.adapter = adapter
    }
    
    fun canClickSendBtn(canSend: Boolean) {
        mDataBinding.btnSendMsg.isEnabled = canSend
        mDataBinding.btnSendMsg.isSelected = canSend
    }
    
    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_send_msg -> {
                mVM.sendMsg(mDataBinding.msgInput.text.toString())
            }
        }
    }
    
    fun addMessage(msg: MessageResponse) {
        adapter.addMessage(msg)
        scrollToDown()
    }
    
    /**
     * 滚动到底部
     */
    private fun scrollToDown() {
        Handler().postDelayed({
            if (adapter.itemCount > 0) {
                mDataBinding.messageRecyclerView.smoothScrollToPosition(adapter.itemCount - 1)
            }
        }, 300)
    }
    
    fun addMessages(msgs: List<MessageResponse>) {
        adapter.addMessages(msgs)
        scrollToDown()
    }
    
    fun clearInput() {
        mDataBinding.msgInput.setText("")
    }
}
