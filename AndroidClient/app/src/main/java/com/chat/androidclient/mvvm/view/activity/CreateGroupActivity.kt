package com.chat.androidclient.mvvm.view.activity

import android.view.View
import com.chat.androidclient.R
import com.chat.androidclient.databinding.ActivityCreateGroupBinding
import com.chat.androidclient.mvvm.viewmodel.CreateGroupVM

/**
 * 创建群组
 */
class CreateGroupActivity : BaseActivity<ActivityCreateGroupBinding, CreateGroupVM>() {
    override fun getViewModel() = CreateGroupVM(this)
    override fun getLayoutRes() = R.layout.activity_create_group
    override fun init() {
        super.init()
        mDataBinding.vm=mVM
    }
    fun changeCommitBtn(b:Boolean){
    mDataBinding.commitBtn.apply {
        isClickable=b
        if (b){
            setBackgroundResource(R.drawable.shape_commit_blue)
        }else{
            setBackgroundResource(R.drawable.shape_commit_gray)
        }
    }
    }
    
    override fun onClick(v: View) {
        when(v.id){
            R.id.commit_btn->mVM.createGroup(mDataBinding.input.text.toString())
        }
    }
}
