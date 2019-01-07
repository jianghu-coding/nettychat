package com.chat.androidclient.mvvm.view.activity

import android.support.v7.widget.LinearLayoutManager
import com.chat.androidclient.R
import com.chat.androidclient.adapter.SearchSimpleAdapter
import com.chat.androidclient.databinding.ActivitySearchPersonBinding
import com.chat.androidclient.mvvm.model.SearchSimpleResult
import com.chat.androidclient.mvvm.viewmodel.SearchPersonVM

class SearchPersonActivity : BaseActivity<ActivitySearchPersonBinding,SearchPersonVM>() {
    override fun getViewModel()= SearchPersonVM (this)
    lateinit var adapter: SearchSimpleAdapter
    
    override fun getLayoutRes()=R.layout.activity_search_person
    override fun init() {
        mDataBinding.vm=mVM
        initRecyclerView()
    }
    
    private fun initRecyclerView() {
        adapter= SearchSimpleAdapter()
        adapter.addData(SearchSimpleResult())
        adapter.addData(SearchSimpleResult())
        mDataBinding.simpleResultRecyclerView.layoutManager= LinearLayoutManager(this)
        mDataBinding.simpleResultRecyclerView.adapter=adapter
    }
 
}
