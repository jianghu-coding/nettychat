package com.chat.androidclient.mvvm.view.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.chat.androidclient.R
import com.chat.androidclient.databinding.FragmentConversationBinding
import com.chat.androidclient.databinding.FragmentDynamicBinding
import com.chat.androidclient.mvvm.viewmodel.ConversationVM
import com.chat.androidclient.mvvm.viewmodel.DynamicVM

/**
 * @author lps
 * 会话Fragment
 *
 */
class DynamicFragment : BaseFragment<FragmentDynamicBinding, DynamicVM>() {
    override fun getViewModel()= DynamicVM()
    
    override fun getLayoutRes()=R.layout.fragment_dynamic
 
    
}
