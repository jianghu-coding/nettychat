package com.chat.androidclient.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.blankj.utilcode.util.SPUtils
import com.chat.androidclient.BR
import com.chat.androidclient.R
import com.chat.androidclient.mvvm.model.Constant
import com.chat.androidclient.mvvm.model.Conversation
import com.chat.androidclient.mvvm.view.activity.ChatActivity

/**
 * Created by lps on 2018/12/27 14:52.
 * to do 优化刷新数据逻辑
 */
class ConversationListAdapter(var context: Context) : RecyclerView.Adapter<VH<*>>() {
    override fun onBindViewHolder(holder: VH<*>, position: Int) {
        holder.binding.setVariable(BR.data,messageList[position])
        holder.binding.root.setOnClickListener {
            var id=messageList[position].fromId
            if (id==SPUtils.getInstance().getLong(Constant.id)){
                id=messageList[position].fromId
            }
            ChatActivity.startActivity(context,messageList[position].fromId) }
    }
    
    private val messageList:MutableList<Conversation> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH<*> {
        val view: ViewDataBinding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), R.layout.item_conversationlist, parent, false)
        return VH(view)
    }
    
    override fun getItemCount()=messageList.size
    
    fun addMessage(s: Conversation) {
        messageList.add(s)
        notifyDataSetChanged()
    }
    
    fun setData(conversations: List<Conversation>) {
        messageList.clear()
        messageList.addAll(conversations)
        notifyDataSetChanged()
    }
    
    
}