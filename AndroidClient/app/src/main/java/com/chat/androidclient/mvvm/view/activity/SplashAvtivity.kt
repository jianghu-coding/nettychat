package com.chat.androidclient.mvvm.view.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.chat.androidclient.R

class SplashAvtivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_avtivity)
        Handler().postDelayed({
            startActivity(Intent(this,LoginActivity::class.java))
        },500)
    }
}
