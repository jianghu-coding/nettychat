package com.chat.androidclient.mvvm.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.blankj.utilcode.util.SPUtils
import com.chat.androidclient.R
import com.chat.androidclient.mvvm.model.Constant

class SplashAvtivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_avtivity)
        Handler().postDelayed({
            val loginstate = SPUtils.getInstance().getBoolean(Constant.LoginState)
            if (loginstate){
                startActivity(Intent(this,MainActivity::class.java))
            }else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        },500)
    }
}
