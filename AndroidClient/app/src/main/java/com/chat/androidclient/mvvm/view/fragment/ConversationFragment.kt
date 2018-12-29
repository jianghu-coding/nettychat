package com.chat.androidclient.mvvm.view.fragment


import android.support.v7.widget.LinearLayoutManager
import com.chat.androidclient.R
import com.chat.androidclient.adapter.ConversationListAdapter
import com.chat.androidclient.databinding.FragmentConversationBinding
import com.chat.androidclient.mvvm.model.Conversation
import com.chat.androidclient.mvvm.viewmodel.ConversationVM
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

/**
 * @author lps
 * 会话Fragment
 *
 */
class ConversationFragment : BaseFragment<FragmentConversationBinding, ConversationVM>() {
    override fun getViewModel() = ConversationVM(this)
    lateinit var adapter: ConversationListAdapter
    override fun getLayoutRes() = R.layout.fragment_conversation
    override fun init() {
        initRecyclerView()
        mDataBinding.vm=mVM
        mDataBinding.refreshlayout.setOnRefreshListener { mVM.init() }
        mVM.init()
    }
    
    private fun initRecyclerView() {
        adapter = ConversationListAdapter(activity)
        mDataBinding.messageRecyclerView.layoutManager = LinearLayoutManager(activity)
        mDataBinding.messageRecyclerView.adapter = adapter
    }
    
    fun refreshConversation(conversations: List<Conversation>) {
        adapter.setData(conversations)
        mDataBinding.refreshlayout.finishRefresh()
    }
    
    fun refreshConversation(conversation: Conversation) {
        adapter.addMessage(conversation)
    }
    
}
