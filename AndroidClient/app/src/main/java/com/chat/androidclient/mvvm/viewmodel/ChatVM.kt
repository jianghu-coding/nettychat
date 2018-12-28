package com.chat.androidclient.mvvm.viewmodel

import android.text.Editable
import android.text.TextWatcher
import com.chat.androidclient.mvvm.view.activity.ChatActivity

/**
 * Created by lps on 2018/12/28 14:00.
 */
class ChatVM(var view: ChatActivity) : BaseViewModel() {
    fun getInputWatcher():TextWatcher=object :TextWatcher{
        override fun afterTextChanged(s: Editable) {
            if (s.length>0){
                view.canClickSendBtn(true)
            }else{
                view.canClickSendBtn(false)
    
            }
        }
    
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
    
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }
}