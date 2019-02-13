package com.chat.androidclient.mvvm.view.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.chat.androidclient.R
import com.chat.androidclient.databinding.ActivityGroupDetailBinding
import com.chat.androidclient.mvvm.viewmodel.GroupDetailVM

class GroupDetailActivity : BaseActivity<ActivityGroupDetailBinding, GroupDetailVM>() {
    override fun getViewModel() = GroupDetailVM(this)
    override fun getLayoutRes() = R.layout.activity_group_detail
    override fun init() {
        mDataBinding.vm = mVM
        mVM.init()
    }
    
    companion object {
        val ID = "id"
        fun startActivity(context: Context, id: Long) {
            val intent = Intent(context, GroupDetailActivity::class.java)
            intent.putExtra(ID, id)
            context.startActivity(intent)
        }
    }
    
    override fun onClick(v: View) {
        when (v.id) {
            R.id.bottombtn -> {
                mVM.joinOrQuitGroup()
            }
        }
    }
}
