package com.chat.androidclient.mvvm.view.activity

import android.content.Intent
import android.view.View
import com.chat.androidclient.R
import com.chat.androidclient.databinding.ActivityLoginBinding
import com.chat.androidclient.mvvm.viewmodel.LoginVM

class LoginActivity : BaseActivity<ActivityLoginBinding,LoginVM>() {
    override fun getViewModel()= LoginVM (this)
    
    override fun getLayoutRes()=R.layout.activity_login
    
    override fun onClick(v: View) {
        when(v.id){
            R.id.signuptv->{
                startActivity(Intent(this,SignUpActivity::class.java))
            }
            R.id.loginbtn->{
                //todo 登陆
            }
        }
    }
}
