package com.chat.androidclient.mvvm.view.activity

import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.AdaptScreenUtils
import com.chat.androidclient.R

class LoginActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
    
    override fun getResources(): Resources {
        return AdaptScreenUtils.adaptWidth(super.getResources(),1080)
    }
    
}
