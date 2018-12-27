package com.chat.androidclient.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chat.androidclient.BR
import com.chat.androidclient.R

/**
 * Created by lps on 2018/12/27 14:52.
 */
class ConversationListAdapter(var context: Context) : RecyclerView.Adapter<VH<*>>() {
    override fun onBindViewHolder(holder: VH<*>, position: Int) {
        holder.binding.setVariable(BR.data,messageList[position])
    }
    
    private val messageList:MutableList<String> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH<*> {
        val view: ViewDataBinding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), R.layout.item_conversationlist, parent, false)
        return VH(view)
    }
    
    override fun getItemCount()=messageList.size
    
    fun addMessage(s: String) {
        messageList.add(s)
        notifyDataSetChanged()
    }
    
    
}