package com.chat.androidclient.mvvm.view.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.blankj.utilcode.util.SPUtils
import com.chat.androidclient.R
import com.chat.androidclient.service.ChatService

class SplashAvtivity : AppCompatActivity() {
    companion object {
        val LOGINSTATE="loginstate"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_avtivity)
        startService(Intent(this,ChatService::class.java))
        Handler().postDelayed({
            val loginstate = SPUtils.getInstance().getBoolean(LOGINSTATE)
            if (loginstate){
                startActivity(Intent(this,MainActivity::class.java))
            }else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        },500)
    }
}
