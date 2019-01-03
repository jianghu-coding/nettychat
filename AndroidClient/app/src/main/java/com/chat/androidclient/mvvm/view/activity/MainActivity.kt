package com.chat.androidclient.mvvm.view.activity

import android.content.Intent
import android.view.View
import com.blankj.utilcode.util.SPUtils
import com.chat.androidclient.R
import com.chat.androidclient.databinding.ActivityMainBinding
import com.chat.androidclient.im.ChatIM
import com.chat.androidclient.mvvm.model.Constant
import com.chat.androidclient.mvvm.model.LoginRequest
import com.chat.androidclient.mvvm.viewmodel.MainVM

class MainActivity : BaseActivity<ActivityMainBinding, MainVM>() {
    override fun getLayoutRes() = R.layout.activity_main
    override fun getViewModel() = MainVM(this)
    
    override fun init() {
        mVM.connect()
        mDataBinding.vm = mVM
        mVM.checkConversation()
    }
    
    override fun onClick(v: View) {
        when (v.id) {
            R.id.re_conversation -> {
                mVM.checkConversation()
            }
            R.id.re_contacts -> {
                mVM.checkContacts()
            }
            R.id.re_dynamic -> {
                mVM.checkDynamic()
            }
            R.id.add_conversation -> {
                startActivity(Intent(this, SearchActivity::class.java))
            }
        }
    }
    
    fun hideLoading() {
        mDataBinding.connectloading.visibility = View.GONE
    }
}
