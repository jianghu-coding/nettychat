package com.chat.androidclient.mvvm.viewmodel

import android.text.Editable
import android.text.TextWatcher
import com.chat.androidclient.mvvm.model.Constant
import com.chat.androidclient.mvvm.procotol.response.SearchFriendResponse
import com.chat.androidclient.mvvm.view.activity.FriendDetailActivity
import com.chat.androidclient.mvvm.view.activity.SearchPersonActivity

/**
 * Created by lps on 2019/1/2 15:08.
 */
class SearchPersonVM(var view: SearchPersonActivity) : BaseViewModel() {
    lateinit var response:SearchFriendResponse
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
    
    fun init() {
        val inputText = view.intent.getStringExtra(Constant.SEARCH_PERSON_INPUT)
        view.addInputText(inputText)
         response = view.intent.getSerializableExtra(Constant.SEARCH_PERSON_LIST) as SearchFriendResponse
        view.refreshRecyclerData(response.users)
    }
    
    fun toFriendDetail(pos: Int) {
      FriendDetailActivity.launchActivity(view,response.users[pos])
    }
}