package com.chat.androidclient

import com.tencent.tinker.loader.app.TinkerApplication
import com.tencent.tinker.loader.shareutil.ShareConstants

/**
 * Created by 李培生 on 2018/12/21 14:08.
 * 接入了tinker 初始化的逻辑写入[AppLike]
 */
class App : TinkerApplication(ShareConstants.TINKER_ENABLE_ALL, "com.chat.androidclient.AppLike",
        "com.tencent.tinker.loader.TinkerLoader", false) {
    companion object {
        var CONNECT=false
    }

}