package com.chat.androidclient.mvvm.viewmodel

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by 李培生 on 2018/12/21 9:45.
 */
open class BaseViewModel {
    init {
        EventBus.getDefault().register(this)
    }
    
    /**
     * 空实现。不然不子类必须写@Subscribe注解的方法。可能有的场景不需要
     */
    @Subscribe
   open fun event(msg:String) {
    
    }
    
  open  fun destroy() {
        EventBus.getDefault().unregister(this)
    }
}