package com.chat.androidclient.mvvm.view.activity

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.chat.androidclient.R
import com.chat.androidclient.adapter.SearchSimpleAdapter
import com.chat.androidclient.databinding.ActivitySearchPersonBinding
import com.chat.androidclient.mvvm.model.Constant
import com.chat.androidclient.mvvm.model.User
import com.chat.androidclient.mvvm.viewmodel.SearchPersonVM

/**
 * 二级搜索界面
 */
class SearchPersonActivity : BaseActivity<ActivitySearchPersonBinding, SearchPersonVM>(), SearchSimpleAdapter.OnItemClickListener {
    override fun onitemClick(pos: Int) {
       mVM.toFriendDetail(pos)
    }
    
    override fun getViewModel() = SearchPersonVM(this)
    lateinit var adapter: SearchSimpleAdapter
    
    override fun getLayoutRes() = R.layout.activity_search_person
    override fun init() {
        mDataBinding.vm = mVM
        initRecyclerView()
        mVM.init()
    }
    
    private fun initRecyclerView() {
        adapter = SearchSimpleAdapter(this)
        adapter.listener=this
        mDataBinding.simpleResultRecyclerView.layoutManager = LinearLayoutManager(this)
        mDataBinding.simpleResultRecyclerView.adapter = adapter
    }
    
    fun addInputText(inputText: String) {
        mDataBinding.etInput.setText(inputText)
    }
    fun  refreshRecyclerData( users:List<User>){
       
            adapter.addDatas(users)
        
    }
    
}
