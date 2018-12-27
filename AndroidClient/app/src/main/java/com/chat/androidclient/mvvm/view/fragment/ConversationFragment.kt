package com.chat.androidclient.mvvm.view.fragment


import android.support.v7.widget.LinearLayoutManager
import com.chat.androidclient.R
import com.chat.androidclient.adapter.ConversationListAdapter
import com.chat.androidclient.databinding.FragmentConversationBinding
import com.chat.androidclient.mvvm.viewmodel.ConversationVM

/**
 * @author lps
 * 会话Fragment
 *
 */
class ConversationFragment : BaseFragment<FragmentConversationBinding, ConversationVM>() {
    override fun getViewModel()= ConversationVM(this)
    
    override fun getLayoutRes()=R.layout.fragment_conversation
    override fun init() {
      initRecyclerView()
    }
    
    private fun initRecyclerView() {
        val adapter = ConversationListAdapter(activity)
        mDataBinding.messageRecyclerView.layoutManager=LinearLayoutManager(activity)
        mDataBinding.messageRecyclerView.adapter= adapter
       adapter.addMessage("")
    }
    
}
