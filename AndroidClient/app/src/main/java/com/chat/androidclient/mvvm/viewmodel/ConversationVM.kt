package com.chat.androidclient.mvvm.viewmodel

import com.chat.androidclient.mvvm.view.fragment.ConversationFragment
import org.greenrobot.eventbus.Subscribe

/**
 * Created by lps on 2018/12/24 15:23.
 */
class ConversationVM(var view: ConversationFragment) : BaseViewModel() {
    init {
        //todo load msg from db
    }
    @Subscribe
 fun  ReciveMessage(){
    
    }
}