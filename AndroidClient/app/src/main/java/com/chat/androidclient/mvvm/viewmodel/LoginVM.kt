package com.chat.androidclient.mvvm.viewmodel

import android.content.Intent
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.chat.androidclient.App
import com.chat.androidclient.event.DestroyLoginEvent
import com.chat.androidclient.event.LoginResponseEvent
import com.chat.androidclient.im.ChatIM
import com.chat.androidclient.mvvm.model.Constant
import com.chat.androidclient.mvvm.model.LoginRequest
import com.chat.androidclient.mvvm.procotol.response.LoginResponse
import com.chat.androidclient.mvvm.view.activity.LoginActivity
import com.chat.androidclient.mvvm.view.activity.MainActivity
import com.chat.androidclient.mvvm.view.custom.LoadingDialog
import org.greenrobot.eventbus.Subscribe

/**
 * Created by 李培生 on 2018/12/21 10:34.
 */
class LoginVM(var view: LoginActivity) : BaseViewModel() {
    private var name: String = ""
    private var pass: String = ""
    
    init {
        name = SPUtils.getInstance().getString(Constant.LoginUserName)
        pass = SPUtils.getInstance().getString(Constant.LoginUserPass)
        view.setNameAndPassToView(name, pass)
    }
    
    fun login(name: String, pass: String) {
        ChatIM.instance.cmd(LoginRequest(name, pass))
        this.name = name
        this.pass = pass
        if (dialog==null){
            dialog= LoadingDialog(view)
        }
        dialog?.show()
        KeyboardUtils.hideSoftInput(view)
    }
    
    /**
     * 登陆的结果
     */
    @Subscribe
    fun loginResponse(event: LoginResponseEvent) {
        name = SPUtils.getInstance().getString(Constant.LoginUserName)
        pass = SPUtils.getInstance().getString(Constant.LoginUserPass)
        if (event.msg.error) {
            view.showMsg("登陆失败了${event.msg.errorInfo}")
        }
        else {
            App.CONNECT = true
            val response = event.msg as LoginResponse
            view.showMsg("登陆成功")
            SPUtils.getInstance().put(Constant.LoginUserName, name)
            SPUtils.getInstance().put(Constant.LoginUserPass, pass)
            SPUtils.getInstance().put(Constant.id, response.userId!!)
            SPUtils.getInstance().put(Constant.LoginState, true)
            view.startActivity(Intent(view, MainActivity::class.java))
            view.finish()
        }
    }
    
    /**
     * 这里必须要销毁解除注册，否则会调用到[loginResponse]
     * 重复写入sp信息。重复拉起MainActivity，而且sp name 和pass 被写为默认空。导致第二次Main登陆报错
     * 用户不存在
     */
    @Subscribe
    fun destroyThisVM(event: DestroyLoginEvent) {
        destroy()
        view.finish()
    }
}