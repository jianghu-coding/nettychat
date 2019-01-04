package com.chat.androidclient

import android.annotation.TargetApi
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.multidex.MultiDex
import com.chat.androidclient.im.ChatIM
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.tinker.entry.DefaultApplicationLike
import org.greenrobot.greendao.query.QueryBuilder


/**
 * Created by lps on 2019/1/4 10:03.
 */
class AppLike(application: Application?, tinkerFlags: Int, tinkerLoadVerifyFlag: Boolean, applicationStartElapsedTime: Long, applicationStartMillisTime: Long, tinkerResultIntent: Intent?) : DefaultApplicationLike(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent) {
    
    override fun onCreate() {
        super.onCreate()
        ChatIM.init()
        Bugly.init(application, "bfa8de2062", true)
        Bugly.setIsDevelopmentDevice(application, true)
//        LogUtils.getConfig().setBorderSwitch(false)
        QueryBuilder.LOG_SQL = true
        QueryBuilder.LOG_VALUES = true
    }
    
    override fun onBaseContextAttached(base: Context?) {
        super.onBaseContextAttached(base)
        MultiDex.install(base)
        Beta.installTinker(this)
        
    }
    
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    fun registerActivityLifecycleCallback(callbacks: Application.ActivityLifecycleCallbacks) {
        application.registerActivityLifecycleCallbacks(callbacks)
    }
}