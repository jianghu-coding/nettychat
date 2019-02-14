package com.chat.androidclient.mvvm.view.activity

import android.content.res.Resources
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast
import com.blankj.utilcode.util.AdaptScreenUtils
import com.blankj.utilcode.util.SPUtils
import com.chat.androidclient.R
import com.chat.androidclient.mvvm.model.Constant
import com.chat.androidclient.mvvm.view.IView
import com.chat.androidclient.mvvm.viewmodel.BaseViewModel
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

open abstract class BaseActivity<T : ViewDataBinding, D : BaseViewModel> : RxAppCompatActivity(), View.OnClickListener, IView {
    lateinit var mVM: D
    
    abstract fun getViewModel(): D
    lateinit var mDataBinding: T
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
    
        super.onCreate(savedInstanceState)
        initDatabinding()
        mVM = getViewModel()
        init()
    }
    
 open fun init() {
    
    }
    
    override fun getResources(): Resources {
        return AdaptScreenUtils.adaptWidth(super.getResources(), 1080)
    }
    
    private fun initDatabinding() {
        this.mDataBinding = DataBindingUtil.setContentView(this, getLayoutRes())
    }
    
    abstract fun getLayoutRes(): Int
    override fun onClick(v: View) {
        //空实现。子Activity 自己选择重写。
    }
    
    /**
     * 统一显示提示消息
     */
    fun showMsg(msg: String?) {
//        check thread 判断是否在主线程。子线程弹出Toast是不行的
        if (Thread.currentThread()!=mainLooper.thread){
            Handler().post {
                Toast.makeText(this,"$msg",Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this,"$msg",Toast.LENGTH_SHORT).show()
        }
    }
    
    fun showDevloadingMsg() {
        Toast.makeText(this,"此功能正在开发",Toast.LENGTH_SHORT).show()
    }
    override fun onDestroy() {
        mVM.destroy()
        super.onDestroy()
    }
}
