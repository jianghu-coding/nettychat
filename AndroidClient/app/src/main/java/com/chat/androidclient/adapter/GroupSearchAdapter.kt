package com.chat.androidclient.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chat.androidclient.R
import com.chat.androidclient.databinding.ItemGrouplistBinding
import com.chat.androidclient.mvvm.model.GroupDTO
import com.chat.androidclient.mvvm.view.activity.GroupDetailActivity

/**
 * Created by lps on 2019/2/11 14:47.
 */
class GroupSearchAdapter(groups: MutableList<GroupDTO>,var context:Context) : RecyclerView.Adapter<VH<*>>() {
    var data: MutableList<GroupDTO> = groups
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH<*> {
        val view: ViewDataBinding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), R.layout.item_grouplist, parent, false)
        return VH(view)
    }
    
    override fun getItemCount() = data.size
    
    override fun onBindViewHolder(holder: VH<*>, position: Int) {
        val binding = holder.binding as ItemGrouplistBinding
        binding.groupname.text = data[position].name
        binding.groupCount.text=""+data[position].num
        binding.root.setOnClickListener {GroupDetailActivity.startActivity(context,data[position].id)  }
    }
    
    fun setGroupsData(obj: List<GroupDTO>?) {
        if (obj == null || obj.isEmpty()) return
        data.clear()
        data.addAll(obj)
        notifyDataSetChanged()
    }
    
}