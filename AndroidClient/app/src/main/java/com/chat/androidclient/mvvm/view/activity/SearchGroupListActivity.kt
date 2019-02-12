package com.chat.androidclient.mvvm.view.activity

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.chat.androidclient.R
import com.chat.androidclient.adapter.GroupSearchAdapter
import com.chat.androidclient.databinding.ActivitySearchGroupListBinding
import com.chat.androidclient.mvvm.procotol.response.SearchGroupResponse
import com.chat.androidclient.mvvm.viewmodel.BaseViewModel

/**
 * 搜索群组的列表。这里只做展示。不做搜索了、就用上个页面跳转携带的数据就好了
 */
class SearchGroupListActivity : BaseActivity<ActivitySearchGroupListBinding,BaseViewModel>() {
    override fun getViewModel()= BaseViewModel()
    
    override fun getLayoutRes()=R.layout.activity_search_group_list
    
    companion object {
        const val DATA="DATA"
        fun startActivity(context: Context, data: SearchGroupResponse){
            val intent = Intent(context,SearchGroupListActivity::class.java)
            intent.putExtra(DATA,data)
            context.startActivity(intent)
        }
    }
    
    override fun init() {
        val response = intent.getSerializableExtra(DATA) as SearchGroupResponse
       mDataBinding.simpleResultRecyclerView.layoutManager=LinearLayoutManager(this)
       mDataBinding.simpleResultRecyclerView.adapter=GroupSearchAdapter(response.groups)
    }
}
