package com.chat.androidclient.mvvm.view.activity

import android.graphics.Color
import android.view.View
import com.blankj.utilcode.util.BarUtils
import com.chat.androidclient.R
import com.chat.androidclient.databinding.ActivitySignUpBinding
import com.chat.androidclient.mvvm.viewmodel.SignUpVM

class SignUpActivity : BaseActivity<ActivitySignUpBinding, SignUpVM>() {
    override fun getViewModel() = SignUpVM(this)
    
    override fun getLayoutRes() = R.layout.activity_sign_up
    override fun onClick(v: View) {
        when (v.id) {
            R.id.back -> finish()
            R.id.signupbtn -> {
//            todo 注册用户
            }
        }
    }
    
}
