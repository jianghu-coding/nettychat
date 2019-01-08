package com.chat.androidclient.mvvm.view.activity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.WindowManager
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SpanUtils
import com.chat.androidclient.R
import com.chat.androidclient.databinding.ActivityFriendDetailBinding
import com.chat.androidclient.mvvm.viewmodel.FriendDetailVM

class FriendDetailActivity : BaseActivity<ActivityFriendDetailBinding,FriendDetailVM>() {
    override fun getViewModel()= FriendDetailVM (this)
    override fun getLayoutRes()=R.layout.activity_friend_detail
    
    override fun init() {
        super.init()
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

//        BarUtils.setStatusBarColor(this,Color.TRANSPARENT)
//        BarUtils.setStatusBarAlpha(this,15)
        mDataBinding.appBar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            LogUtils.e(""+verticalOffset)
//            LogUtils.e(""+mDataBinding.appBar.totalScrollRange)
            if (Math.abs(verticalOffset)>=mDataBinding.appBar.totalScrollRange){
//                mDataBinding.collapslayout.contentScrim=ColorDrawable(Color.parseColor("#41c5f6"))
//            mDataBinding.titleLayout.setBackgroundColor(resources.getColor(R.color.color41c5f6))
            }else{
//                mDataBinding.titleLayout.setBackgroundColor(0)
    
            }
        }
    }
}
