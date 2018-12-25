package com.chat.androidclient.mvvm.viewmodel

import android.content.Intent
import com.chat.androidclient.mvvm.model.Command
import com.chat.androidclient.mvvm.model.LoginRequest
import com.chat.androidclient.mvvm.view.activity.LoginActivity
import com.chat.androidclient.network.NeetClient
import com.chat.androidclient.service.ChatService

/**
 * Created by 李培生 on 2018/12/21 10:34.
 */
class LoginVM(var view: LoginActivity) : BaseViewModel {
    fun login(name:String,pass:String){
        val intent = Intent()
        intent.action="chatcommand"
        intent.putExtra(ChatService.CMD,Command.LOGIN)
        intent.putExtra(ChatService.EXTRA,  LoginRequest(name,pass))
        view.sendBroadcast(intent)
    }
    
  
}