package com.chat.androidclient.mvvm.view.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.chat.androidclient.R
import com.chat.androidclient.databinding.FragmentContactsBinding
import com.chat.androidclient.databinding.FragmentConversationBinding
import com.chat.androidclient.mvvm.viewmodel.ContactsVM
import com.chat.androidclient.mvvm.viewmodel.ConversationVM

/**
 * @author lps
 * 会话Fragment
 *
 */
class ContactsFragment : BaseFragment<FragmentContactsBinding, ContactsVM>() {
    override fun getViewModel()= ContactsVM()
    
    override fun getLayoutRes()=R.layout.fragment_contacts
 
    
}
