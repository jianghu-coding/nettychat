package com.chat.androidclient.mvvm.view.activity

import com.chat.androidclient.R
import com.chat.androidclient.databinding.ActivityFriendDetailBinding
import com.chat.androidclient.mvvm.viewmodel.FriendDetailVM

class FriendDetailActivity : BaseActivity<ActivityFriendDetailBinding,FriendDetailVM>() {
    override fun getViewModel()= FriendDetailVM (this)
    override fun getLayoutRes()=R.layout.activity_friend_detail
    
 
}
