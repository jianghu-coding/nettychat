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
import com.chat.androidclient.mvvm.model.ConverSationTYPE
import com.chat.androidclient.mvvm.procotol.response.MessageResponse
import com.chat.androidclient.mvvm.viewmodel.ChatVM
import com.zhihu.matisse.Matisse

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
        val REQUEST_CODE_IMAGE = 0x123
        @JvmStatic
        fun startActivity(context: Context, chatId: Long, mConverSationType: ConverSationTYPE) {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(ID, chatId)
            intent.putExtra(TYPE, mConverSationType)
            context.startActivity(intent)
        }
        
        @JvmStatic
        fun startActivity(context: Context, chatId: Long, msg: String) {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(ID, chatId)
            intent.putExtra(MSG, msg)
            intent.putExtra(TYPE, ConverSationTYPE.PERSON)
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
    
    fun setConversationTitle(info: String) {
        mDataBinding.converUser.text = info
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
                mVM.sendTextMsg(mDataBinding.msgInput.text.toString())
            }
            R.id.type_image -> {
                mVM.choosePic()
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
    
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_IMAGE) {
            val pathResult = Matisse.obtainPathResult(data)
            if (pathResult == null || pathResult.isEmpty()) {
                showMsg("选择图片失败")
                return
            }
            mVM.uploadImage(pathResult[0])
        }
    }
}
