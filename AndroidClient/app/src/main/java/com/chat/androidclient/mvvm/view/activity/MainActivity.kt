package com.chat.androidclient.mvvm.view.activity

import android.content.Intent
import android.view.Gravity
import android.view.View
import com.blankj.utilcode.util.BarUtils
import com.chat.androidclient.R
import com.chat.androidclient.databinding.ActivityMainBinding
import com.chat.androidclient.mvvm.viewmodel.MainVM

class MainActivity : BaseActivity<ActivityMainBinding, MainVM>() {
    override fun getLayoutRes() = R.layout.activity_main
    override fun getViewModel() = MainVM(this)
    
    override fun init() {
        mVM.connect()
        mDataBinding.vm = mVM
        mVM.checkConversation()
        //处理状态栏效果
        initStatusBar()
//        mDataBinding.drawerlayout.tou
    }
    
    private fun initStatusBar() {
        BarUtils.setStatusBarAlpha4Drawer(this, mDataBinding.drawerlayout, mDataBinding.fakebar, 0, false)
        BarUtils.addMarginTopEqualStatusBarHeight(mDataBinding.titleLayout)
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
            R.id.head -> {
                if (mDataBinding.drawerlayout.isDrawerOpen(Gravity.LEFT))
                mDataBinding.drawerlayout.closeDrawer(Gravity.LEFT)
                else{
                    mDataBinding.drawerlayout.openDrawer(Gravity.LEFT)
                }
            }
            R.id.titlt_more -> showDevloadingMsg()
            R.id.tv_more -> showDevloadingMsg()
            R.id.iv_myhead->{
                startActivity(Intent(this, FriendDetailActivity::class.java))
            }
        }
    }
    
    fun hideLoading() {
        mDataBinding.connectloading.visibility = View.GONE
    }
    
    fun showLoading() {
        mDataBinding.connectloading.visibility = View.VISIBLE
        
    }
    
    override fun onBackPressed() {
        moveTaskToBack(false)
    }
}
