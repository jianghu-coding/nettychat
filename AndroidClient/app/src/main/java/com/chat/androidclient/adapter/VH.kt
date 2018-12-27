package com.chat.androidclient.adapter

import android.databinding.ViewDataBinding

/**
 * Created by lps on 2018/12/27 15:31.
 */
class VH<out T: ViewDataBinding>(val binding: T) : android.support.v7.widget.RecyclerView.ViewHolder(binding.root)
