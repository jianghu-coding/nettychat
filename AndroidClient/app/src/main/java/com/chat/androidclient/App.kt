package com.chat.androidclient

import android.app.Application
import android.support.multidex.MultiDex

/**
 * Created by 李培生 on 2018/12/21 14:08.
 */
class App:Application() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }
}