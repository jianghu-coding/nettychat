package com.chat.androidclient.mvvm.view.activity

import android.view.View
import com.chat.androidclient.R
import com.chat.androidclient.databinding.ActivitySearchBinding
import com.chat.androidclient.mvvm.viewmodel.SearchVM

/**
 * @author lps
 * 搜索Activity
 * to do 扩展为 聊天记录搜索。好友搜索，群搜索等等
 * 不同的入口传入不同的TYPE 做不同的操作
 */
class SearchActivity : BaseActivity<ActivitySearchBinding, SearchVM>() {
    override fun getViewModel() = SearchVM(this)
    override fun getLayoutRes() = R.layout.activity_search
    override fun init() {
        mDataBinding.vm = mVM
        
    }
    
   
    
    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_cancle -> {
                finish()
            }
            R.id.re_se_person->{
            mVM.SearchPerson()
            }
        }
    }
}
