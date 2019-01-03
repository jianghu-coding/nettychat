package com.chat.androidclient.mvvm.viewmodel

import android.content.Intent
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.chat.androidclient.event.DestroyLoginEvent
import com.chat.androidclient.event.SignUpResponseEvent
import com.chat.androidclient.im.ChatIM
import com.chat.androidclient.mvvm.model.Constant
import com.chat.androidclient.mvvm.model.RegisterRequest
import com.chat.androidclient.mvvm.procotol.response.RegisterResponse
import com.chat.androidclient.mvvm.view.activity.MainActivity
import com.chat.androidclient.mvvm.view.activity.SignUpActivity
import okhttp3.OkHttpClient
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by 李培生 on 2018/12/21 10:22.
 */
class SignUpVM(var view: SignUpActivity) : BaseViewModel() {
    private var name=""
    private var pass=""
    fun signup(name:String,username:String,password:String){
        this.name=name
        this.pass=password
        ChatIM.instance.cmd( RegisterRequest(name,username,password))
//        SPUtils.getInstance().put(Constant.LoginUserName,name)
//        SPUtils.getInstance().put(Constant.LoginUserPass,pass)
    }
    @Subscribe
    fun onSignupResponse(event: SignUpResponseEvent){
        val response = event.msg as RegisterResponse
        if (!response.error){
            view.showMsg("注册成功")
            SPUtils.getInstance().put(Constant.LoginUserName,name)
            SPUtils.getInstance().put(Constant.LoginUserPass,pass)
            SPUtils.getInstance().put(Constant.id,response.userId!!)
            SPUtils.getInstance().put(Constant.LoginState,true)
            // 销毁登陆Activity,不然以现在的逻辑在MainActivity连接的时候会调用LoginVM的方法
            EventBus.getDefault().post(DestroyLoginEvent())
            view.startActivity(Intent(view,MainActivity::class.java))
            view.finish()
        }else{
            ToastUtils.showShort("失败：${response.errorInfo}")
        }
    }
}