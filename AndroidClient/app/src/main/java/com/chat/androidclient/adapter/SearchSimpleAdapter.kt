package com.chat.androidclient.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chat.androidclient.R
import com.chat.androidclient.mvvm.model.User

/**
 * Created by lps on 2019/1/7 10:56.
 */
class SearchSimpleAdapter(var context: Context) : RecyclerView.Adapter<SearchSimpleAdapter.VH>() {
    var resultList: MutableList<User> = mutableListOf()
    var listener: OnItemClickListener? = null
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_searchperson, null)
        return VH(view)
    }
    
    override fun getItemCount() = resultList.size
    
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.name.text = resultList[position].username
        holder.rootView.setOnClickListener {
            if (listener != null) {
                listener!!.onitemClick(position)
            }
        }
    }
    
    
    fun addData(data: User) {
        resultList.add(data)
        notifyDataSetChanged()
    }
    
    fun addDatas(data: List<User>) {
        resultList.clear()
        resultList.addAll(data)
        notifyDataSetChanged()
    }
    
    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val rootView: View = view.findViewById(R.id.rootview)
    }
    
    interface OnItemClickListener {
        fun onitemClick(pos: Int)
    }
}