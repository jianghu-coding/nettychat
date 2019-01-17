package com.chat.androidclient.mvvm.viewmodel

import com.chat.androidclient.event.ThemeEvent
import com.chat.androidclient.mvvm.view.fragment.DynamicFragment
import org.greenrobot.eventbus.Subscribe

/**
 * Created by lps on 2018/12/24 15:23.
 */
class DynamicVM(var view: DynamicFragment) : BaseViewModel() {
    
    @Subscribe
    fun  themeChange(event: ThemeEvent){
        view.refreshUI()
    }
}