package com.chat.androidclient.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chat.androidclient.R
import com.chat.androidclient.mvvm.model.SearchSimpleResult

/**
 * Created by lps on 2019/1/7 10:56.
 */
class SearchSimpleAdapter : RecyclerView.Adapter<SearchSimpleAdapter.VH>() {
    var resultList: MutableList<SearchSimpleResult> = mutableListOf()
    
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_searchperson, null)
                return VH(view)
    }
    
    override fun getItemCount() = resultList.size
    
    override fun onBindViewHolder(holder: VH?, position: Int) {
    }
    

    fun addData(data:SearchSimpleResult){
        resultList.add(data)
        notifyDataSetChanged()
    }
    class VH(view: View) : RecyclerView.ViewHolder(view) {}
}