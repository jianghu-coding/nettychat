package com.chat.androidclient.mvvm.viewmodel

import android.content.Intent
import android.databinding.ObservableField
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import com.blankj.utilcode.util.LogUtils
import com.chat.androidclient.event.SearchFriendResponseEvent
import com.chat.androidclient.event.SearchGroupResponseEvent
import com.chat.androidclient.im.ChatIM
import com.chat.androidclient.mvvm.model.Constant
import com.chat.androidclient.mvvm.procotol.request.SearchFriendRequest
import com.chat.androidclient.mvvm.procotol.request.SearchGroupRequest
import com.chat.androidclient.mvvm.procotol.response.SearchFriendResponse
import com.chat.androidclient.mvvm.procotol.response.SearchGroupResponse
import com.chat.androidclient.mvvm.view.activity.FriendDetailActivity
import com.chat.androidclient.mvvm.view.activity.SearchActivity
import com.chat.androidclient.mvvm.view.activity.SearchGroupListActivity
import com.chat.androidclient.mvvm.view.activity.SearchPersonActivity
import com.chat.androidclient.mvvm.view.custom.LoadingDialog
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

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
        if (dialog == null) {
            dialog = LoadingDialog(view)
        }
        dialog!!.show()
        try {
            text.get()!!.toInt()
            /**
             * 看了QQ的效果。猜测是大于4位纯数字用精确查找QQ号码。其他情况是模糊搜索的
             */
            if (text.get()!!.length > 4) {
                SearchByUserName()
            }
            else {
                SearchByName()
            }
        }
        catch (e: NumberFormatException) {
            SearchByName()
        }
        
    }
    
    /**
     * 通过用户名模糊搜索
     */
    private fun SearchByName() {
        val request = SearchFriendRequest(text.get(), null)
        ChatIM.instance.cmd(request)
    }
    
    /**
     * 精确搜索
     */
    private fun SearchByUserName() {
        val request = SearchFriendRequest(null, text.get())
        ChatIM.instance.cmd(request)
    }
    
    /**
     * 搜索群组
     */
    fun SearchGroup() {
        val request = SearchGroupRequest(text.get())
        ChatIM.instance.cmd(request)
        if (dialog == null) {
            dialog = LoadingDialog(view)
        }
        dialog!!.show()
    }
    
    @Subscribe
    fun searchEvent(event: SearchFriendResponseEvent) {
        dialog?.dismiss()
        val response = event.msg as SearchFriendResponse
        if (response.users.size > 1) {
            /**
             * 如果结果不止一个。进入二级搜索继续筛选
             */
            val intent = Intent(view, SearchPersonActivity::class.java)
            intent.putExtra(Constant.SEARCH_PERSON_INPUT, text.get())
            intent.putExtra(Constant.SEARCH_PERSON_LIST, response)
            view.startActivity(intent)
        }
        else if (response.users.size == 1) {
            /**
             * 如果只有唯一的结果。进入他的详情页
             */
            FriendDetailActivity.launchActivity(view, response.users[0])
        }
        else {
            view.showMsg("没有搜索到你要找的人")
        }
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun searchGroupEvent(event: SearchGroupResponseEvent) {
        dialog?.dismiss()
        val response = event.msg as SearchGroupResponse
        if (response.error) {
            view.showMsg(response.errorInfo);return
        }
        if (response.groups.isEmpty()) {
            view.showMsg("没有搜索到“${text.get()}”的结果")
        }
//         跳转到搜索到的群组列表
        SearchGroupListActivity.startActivity(view,response)
    }
}