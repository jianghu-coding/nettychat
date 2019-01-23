package com.chat.androidclient.mvvm.view.activity

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.blankj.utilcode.util.BarUtils
import com.chat.androidclient.R
import com.chat.androidclient.databinding.ActivityFriendDetailBinding
import com.chat.androidclient.mvvm.viewmodel.FriendDetailVM
import kotlinx.android.synthetic.main.activity_friend_detail.view.*

class FriendDetailActivity : BaseActivity<ActivityFriendDetailBinding,FriendDetailVM>() {
    override fun getViewModel()= FriendDetailVM (this)
    override fun getLayoutRes()=R.layout.activity_friend_detail
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.profilestyle)
        super.onCreate(savedInstanceState)
    }
    override fun init() {
        super.init()
        mDataBinding.appBar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset)>=mDataBinding.appBar.totalScrollRange){
//                收起
                mDataBinding.titleLayout.tv_title.visibility= VISIBLE
                BarUtils.setStatusBarColor(this,resources.getColor(R.color.color41c5f6),0)
            }else{
//                展开
                BarUtils.setStatusBarColor(this,resources.getColor(android.R.color.transparent),0)
                mDataBinding.titleLayout.tv_title.visibility=GONE
            }
        }
    }
    
    override fun onClick(v: View) {
        when(v.id){
            R.id.back->{finish()}
            R.id.add_friend->{showDevloadingMsg()}
            
        }
    }
}
