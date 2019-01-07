package com.chat.androidclient.mvvm.viewmodel

import android.databinding.ObservableField
import android.text.Editable
import android.text.TextWatcher
import com.chat.androidclient.mvvm.view.activity.SearchActivity
import com.chat.androidclient.mvvm.view.custom.LoadingDialog

/**
 * Created by lps on 2019/1/2 15:08.
 */
class SearchVM(var view: SearchActivity) : BaseViewModel() {
    val text: ObservableField<String> = ObservableField("")
    fun getInputWatcher(): TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            text.set(s.toString())
        }
        
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }
    
    
    fun SearchPerson() {
        //        todo 发送搜索指令,在Response 如果是多结果跳转到[com.chat.androidclient.mvvm.view.activity.SearchPersonActivity]
//        如果只有一个人符合。，就跳转到个人详情
        LoadingDialog(view).show()
        
    }
}