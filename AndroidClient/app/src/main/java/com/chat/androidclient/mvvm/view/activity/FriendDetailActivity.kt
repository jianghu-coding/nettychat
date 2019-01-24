package com.chat.androidclient.mvvm.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.SPUtils
import com.chat.androidclient.R
import com.chat.androidclient.databinding.ActivityFriendDetailBinding
import com.chat.androidclient.mvvm.model.Constant
import com.chat.androidclient.mvvm.model.User
import com.chat.androidclient.mvvm.viewmodel.FriendDetailVM
import kotlinx.android.synthetic.main.activity_friend_detail.view.*

class FriendDetailActivity : BaseActivity<ActivityFriendDetailBinding, FriendDetailVM>() {
    override fun getViewModel() = FriendDetailVM(this)
    override fun getLayoutRes() = R.layout.activity_friend_detail
    
    companion object {
        fun launchActivity(context: Context, info: User) {
            val intent = Intent(context, FriendDetailActivity::class.java)
            intent.putExtra(Constant.FRIENDDETAIL_USER_INFO, info)
            context.startActivity(intent)
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.profilestyle)
        super.onCreate(savedInstanceState)
    }
    
    override fun init() {
        mVM.init()
        mDataBinding.vm = mVM
        super.init()
//        添加好友的按钮显示隐藏逻辑
//        如果是自己进自己的主页。不显示。。其他情况显示
//        （to do） 以后需要优化为如果不是好友。显示。如果已经是好友了。显示发送消息。是自己就什么都不显示
        mDataBinding.bottomLayout.run {
            if (mVM.user.get()!!.id == SPUtils.getInstance().getLong(Constant.id))
                this.visibility = GONE
            else
                this.visibility = VISIBLE
        }
        mDataBinding.appBar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset) >= mDataBinding.appBar.totalScrollRange) {
//                收起
                mDataBinding.titleLayout.tv_title.visibility = VISIBLE
                BarUtils.setStatusBarColor(this, resources.getColor(R.color.color41c5f6), 0)
            }
            else {
//                展开
                BarUtils.setStatusBarColor(this, resources.getColor(android.R.color.transparent), 0)
                mDataBinding.titleLayout.tv_title.visibility = GONE
            }
        }
    }
    
    override fun onClick(v: View) {
        when (v.id) {
            R.id.back -> {
                finish()
            }
            R.id.add_friend -> {
                mVM.addFriend()
            }
            
        }
    }
}
