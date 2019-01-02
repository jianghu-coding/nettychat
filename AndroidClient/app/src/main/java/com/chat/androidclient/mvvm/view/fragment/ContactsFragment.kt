package com.chat.androidclient.mvvm.view.fragment


import com.chat.androidclient.R
import com.chat.androidclient.adapter.FriendAdapter
import com.chat.androidclient.databinding.FragmentContactsBinding
import com.chat.androidclient.mvvm.model.Group
import com.chat.androidclient.mvvm.viewmodel.ContactsVM

/**
 * @author lps
 * 会话Fragment
 *
 */
class ContactsFragment : BaseFragment<FragmentContactsBinding, ContactsVM>() {
    override fun getViewModel() = ContactsVM(this)
    lateinit var friendAdapter: FriendAdapter
    
    override fun getLayoutRes() = R.layout.fragment_contacts
    override fun init() {
        initFriendList()
        mVM.init()
    }
    
    private fun initFriendList() {
        friendAdapter = FriendAdapter(activity)
        mDataBinding.listviewFriends.setAdapter(friendAdapter)
        mDataBinding.listviewFriends.setGroupIndicator(null)
    }
    
    fun refreshData(datas:MutableList<Group>){
        friendAdapter.setData(datas)
    }
    
    
}
