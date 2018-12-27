package com.chat.androidclient.mvvm.viewmodel

import android.content.Intent
import com.chat.androidclient.event.LoginResponseEvent
import com.chat.androidclient.im.ChatIM
import com.chat.androidclient.mvvm.model.LoginRequest
import com.chat.androidclient.mvvm.view.activity.LoginActivity
import com.chat.androidclient.mvvm.view.activity.MainActivity
import org.greenrobot.eventbus.Subscribe

/**
 * Created by 李培生 on 2018/12/21 10:34.
 */
class LoginVM(var view: LoginActivity) : BaseViewModel() {
    fun login(name: String, pass: String) {
        ChatIM.instance.cmd(LoginRequest(name, pass))
    }
    
  
    @Subscribe
    fun loginResponse(event: LoginResponseEvent) {
        if (event.msg.error) {
            view.showMsg("登陆失败${event.msg.errorInfo}")
        }
        else {
            view.showMsg("登陆成功")
            view.startActivity(Intent(view, MainActivity::class.java))
        }
    }
    
}