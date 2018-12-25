package com.chat.androidclient.mvvm.viewmodel

import android.content.Intent
import com.chat.androidclient.mvvm.model.Command
import com.chat.androidclient.mvvm.model.LoginRequest
import com.chat.androidclient.mvvm.model.RegisterRequest
import com.chat.androidclient.mvvm.view.activity.SignUpActivity
import com.chat.androidclient.service.ChatService

/**
 * Created by 李培生 on 2018/12/21 10:22.
 */
class SignUpVM(var view: SignUpActivity) : BaseViewModel {
    fun signup(name:String,username:String,password:String){
        val intent = Intent()
        intent.action="chatcommand"
        intent.putExtra(ChatService.CMD, Command.LOGIN)
        intent.putExtra(ChatService.EXTRA,  RegisterRequest(name,username,password))
        view.sendBroadcast(intent)
    }
}