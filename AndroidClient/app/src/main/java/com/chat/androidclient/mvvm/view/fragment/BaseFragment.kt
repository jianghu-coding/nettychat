package com.chat.androidclient.mvvm.view.fragment

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chat.androidclient.mvvm.view.IView
import com.chat.androidclient.mvvm.viewmodel.BaseViewModel
import com.trello.rxlifecycle2.components.RxFragment

/**
 * Created by lps on 2018/12/24 15:15.
 */
open abstract class BaseFragment<T:ViewDataBinding,D:BaseViewModel>:RxFragment() ,IView,View.OnClickListener{
    lateinit var mVM: D
    
    abstract fun getViewModel(): D
    lateinit var mDataBinding: T
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View {
       mDataBinding=DataBindingUtil.inflate(inflater,getLayoutRes(),container,false)
        mVM=getViewModel()
        init()
        return mDataBinding.root
    }
    
    open fun init(){
    
    }
    
    abstract fun getLayoutRes(): Int
    override fun onClick(v: View) {
    
    }
}