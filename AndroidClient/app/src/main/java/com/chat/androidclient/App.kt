package com.chat.androidclient

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import android.support.v7.app.AppCompatDelegate
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.chat.androidclient.im.ChatIM
import com.chat.androidclient.mvvm.model.Constant
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.header.BezierRadarHeader
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import org.greenrobot.greendao.query.QueryBuilder

/**
 * Created by 李培生 on 2018/12/21 14:08.
 */
class App:Application() {
    companion object {
        var CONNECT=false
    }
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        ChatIM.init()
        LogUtils.getConfig()
                .setBorderSwitch(false)
                .setLogHeadSwitch(false)
        QueryBuilder.LOG_SQL=true
        QueryBuilder.LOG_VALUES=true
        Bugly.init(this,"bfa8de2062",true)
        //下拉刷新样式配置
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout -> ClassicsHeader(context) }
    }
    
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        // 安装tinker
        Beta.installTinker();
    }
}