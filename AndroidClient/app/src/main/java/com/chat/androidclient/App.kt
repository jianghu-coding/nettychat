package com.chat.androidclient

import android.app.Application
import android.support.multidex.MultiDex
import com.blankj.utilcode.util.LogUtils
import com.chat.androidclient.im.ChatIM
import com.tencent.tinker.loader.app.TinkerApplication
import com.tencent.tinker.loader.shareutil.ShareConstants
import org.greenrobot.greendao.query.QueryBuilder

/**
 * Created by 李培生 on 2018/12/21 14:08.
 */
class App : TinkerApplication {
    constructor() :  super(ShareConstants.TINKER_ENABLE_ALL, "com.chat.androidclient.AppLike",
            "com.tencent.tinker.loader.TinkerLoader", false)
    
    companion object {
        var CONNECT=false
    }

}