package com.chat.androidclient

import android.app.Application
import android.support.multidex.MultiDex
import com.blankj.utilcode.util.LogUtils
import com.chat.androidclient.im.ChatIM

/**
 * Created by 李培生 on 2018/12/21 14:08.
 */
class App:Application() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        ChatIM.init()
//        LogUtils.getConfig().setBorderSwitch(false)
    }
}