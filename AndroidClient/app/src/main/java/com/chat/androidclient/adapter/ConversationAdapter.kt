package com.chat.androidclient.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.TimeUtils
import com.chat.androidclient.R
import com.chat.androidclient.mvvm.model.Constant
import com.chat.androidclient.mvvm.procotol.response.MessageResponse

/**
 * Created by lps on 2019/1/2 16:38.
 */
class ConversationAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        val TYPE_SEND_TEXT = 1
        val TYPE_REC_TEXT = 2
    }
    
    var messageList: MutableList<MessageResponse> = mutableListOf()
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when(getItemViewType(position)){
            TYPE_SEND_TEXT->{
                val vH_SEND = holder as VH_SEND
                vH_SEND.content.text=messageList[position].message
                vH_SEND.time.text=TimeUtils.getFriendlyTimeSpanByNow(messageList[position].time)
            }
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_SEND_TEXT -> {
                val inflate = LayoutInflater.from(context).inflate(R.layout.item_sendmsg, null)
                return VH_SEND(inflate)
            }
            else -> {
                throw Exception("没有匹配的类型")
            }
        }
    }
    
    override fun getItemViewType(position: Int): Int {
        val response = messageList[position]
        if (response.fromUserId == SPUtils.getInstance().getLong(Constant.id)) {
            return TYPE_SEND_TEXT
        }
        else {
            return TYPE_REC_TEXT
        }
        return super.getItemViewType(position)
    }
    
    override fun getItemCount() = messageList.size
    
    fun addMessage(msg:MessageResponse){
        messageList.add(msg)
        notifyDataSetChanged()
    }
    
    fun  addMessages(msgs:List<MessageResponse>){
        messageList.addAll(msgs)
        notifyDataSetChanged()
    }
    
    class VH_SEND(view: View) : RecyclerView.ViewHolder(view) {
     var content=view.findViewById<TextView>(R.id.content)
     var time=view.findViewById<TextView>(R.id.time)
    }
    
    class VH_REC(view: View) : RecyclerView.ViewHolder(view) {
    
    }
}
