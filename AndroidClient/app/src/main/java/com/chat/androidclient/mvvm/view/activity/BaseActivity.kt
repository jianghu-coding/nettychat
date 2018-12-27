package com.chat.androidclient.mvvm.view.activity

import android.content.res.Resources
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.blankj.utilcode.util.AdaptScreenUtils
import com.blankj.utilcode.util.SnackbarUtils
import com.blankj.utilcode.util.ToastUtils
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
    fun showMsg(msg: String) {
        Snackbar.make(mDataBinding.root,msg,Snackbar.LENGTH_SHORT).show()
    }
    
    override fun onDestroy() {
        mVM.destroy()
        super.onDestroy()
    }
}
