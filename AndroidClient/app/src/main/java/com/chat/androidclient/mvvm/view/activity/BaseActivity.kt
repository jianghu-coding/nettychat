package com.chat.androidclient.mvvm.view.activity

import android.content.res.Resources
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.Snackbar
import android.view.View
import com.blankj.utilcode.util.AdaptScreenUtils
import com.blankj.utilcode.util.SnackbarUtils
import com.blankj.utilcode.util.ToastUtils
import com.chat.androidclient.mvvm.view.IView
import com.chat.androidclient.mvvm.viewmodel.BaseViewModel
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import java.io.File

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
    
    fun showDevlopingMsg() {
        val directory = Environment.getExternalStorageDirectory()
        var filed=directory.path+"/chat/tinker"
        if (File(filed).exists()){
            ToastUtils.showShort("文件存在")
        }else{
            ToastUtils.showShort("文件不存在，准备创建")
            try {
    
                File(filed).mkdirs()
                File(filed+"/1.txt").createNewFile()
            }catch (e :Exception){
                ToastUtils.showShort("文件不存在，准备创建${e.message}")
    
            }
        }
        Snackbar.make(mDataBinding.root,"此功能正在开发",Snackbar.LENGTH_SHORT).show()
    }
    override fun onDestroy() {
        mVM.destroy()
        super.onDestroy()
    }
}
