package com.chat.androidclient.mvvm.view.activity

import android.os.Bundle
import com.chat.androidclient.R
import com.chat.androidclient.databinding.ActivitySearchBinding
import com.chat.androidclient.mvvm.viewmodel.SearchVM

class SearchActivity : BaseActivity<ActivitySearchBinding,SearchVM>() {
    override fun getViewModel()=SearchVM (this)
    
    override fun getLayoutRes()=R.layout.activity_search
    override fun init() {
    
    }
}
