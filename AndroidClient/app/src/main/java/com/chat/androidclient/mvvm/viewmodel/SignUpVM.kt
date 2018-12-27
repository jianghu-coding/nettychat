package com.chat.androidclient.mvvm.viewmodel

import com.chat.androidclient.event.SignUpResponseEvent
import com.chat.androidclient.im.ChatIM
import com.chat.androidclient.mvvm.model.RegisterRequest
import com.chat.androidclient.mvvm.view.activity.SignUpActivity
import okhttp3.OkHttpClient
import org.greenrobot.eventbus.Subscribe

/**
 * Created by 李培生 on 2018/12/21 10:22.
 */
class SignUpVM(var view: SignUpActivity) : BaseViewModel() {
    fun signup(name:String,username:String,password:String){
        ChatIM.instance.cmd( RegisterRequest(name,username,password))
    }
    @Subscribe
    fun onSignupResponse(event: SignUpResponseEvent){
    
    }
}