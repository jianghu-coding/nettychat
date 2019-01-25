package com.chat.androidclient.mvvm.viewmodel

import com.chat.androidclient.mvvm.view.custom.LoadingDialog
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by 李培生 on 2018/12/21 9:45.
 */
open class BaseViewModel {
    var dialog: LoadingDialog?=null
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
      if (dialog!=null&&dialog!!.isShowing){
          dialog?.dismiss()
      }
        EventBus.getDefault().unregister(this)
    }
}