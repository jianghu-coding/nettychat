package com.chat.androidclient.mvvm.viewmodel

import android.text.Editable
import android.text.TextWatcher
import com.chat.androidclient.mvvm.view.activity.SearchPersonActivity

/**
 * Created by lps on 2019/1/2 15:08.
 */
class SearchPersonVM(var view: SearchPersonActivity) : BaseViewModel() {
    fun getInputWatcher(): TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
//            if (s.isNotEmpty()) {
//                search(s.toString())
//            }
        }
        
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }
    
    fun search(str: String) {
//        todo 发送搜索指令
    }
}