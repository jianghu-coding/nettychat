package com.chat.androidclient.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chat.androidclient.BR
import com.chat.androidclient.R
import com.chat.androidclient.databinding.ItemConversationlistBinding
import com.chat.androidclient.greendao.ConversationDao
import com.chat.androidclient.greendao.DaoMaster
import com.chat.androidclient.greendao.FriendDao
import com.chat.androidclient.mvvm.model.Constant
import com.chat.androidclient.mvvm.model.Conversation
import com.chat.androidclient.mvvm.view.activity.ChatActivity
import com.chat.androidclient.util.TimeUtils

/**
 * Created by lps on 2018/12/27 14:52.
 * to do 优化刷新数据逻辑
 */
class ConversationListAdapter(var context: Context) : RecyclerView.Adapter<VH<*>>() {
    override fun onBindViewHolder(holder: VH<*>, position: Int) {
        holder.binding.setVariable(BR.data,messageList[position])
        val binding = holder.binding as ItemConversationlistBinding
        val devSession = DaoMaster.newDevSession(context, Constant.DBNAME)
        val friendBuilder = devSession.friendDao.queryBuilder()
        val friend = friendBuilder.where(FriendDao.Properties.UserId.eq(messageList[position].fromId)).unique()
        var nickname :String=""
        if (friend!=null){
         nickname   =   friend!!.nickname
       }else{
            nickname=messageList[position].fromId.toString()
        }
        val convBuilder = devSession.conversationDao.queryBuilder()
        val conversation = convBuilder.where(ConversationDao.Properties.FromId.eq(messageList[position].fromId)).unique()
        binding.name.text = nickname
        binding.content.text = conversation.lastcontent
        binding.time.text = TimeUtils.getTimeFriend(conversation.time)
        holder.binding.root.setOnClickListener { ChatActivity.startActivity(context,messageList[position].fromId) }
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