package com.chat.androidclient.mvvm.view.activity

import android.view.View
import com.chat.androidclient.R
import com.chat.androidclient.databinding.ActivitySearchBinding
import com.chat.androidclient.mvvm.viewmodel.SearchVM

class SearchActivity : BaseActivity<ActivitySearchBinding,SearchVM>() {
    override fun getViewModel()=SearchVM (this)
    
    override fun getLayoutRes()=R.layout.activity_search
    override fun init() {
    mDataBinding.vm=mVM
    }
    
    override fun onClick(v: View) {
        when(v.id){
            R.id.tv_cancle->{
                finish()
            }
        }
    }
}
