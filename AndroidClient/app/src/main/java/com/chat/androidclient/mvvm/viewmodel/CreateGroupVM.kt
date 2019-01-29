package com.chat.androidclient.mvvm.viewmodel

import android.text.Editable
import android.text.TextWatcher
import com.chat.androidclient.event.CreateGroupResponseEvent
import com.chat.androidclient.greendao.DaoMaster
import com.chat.androidclient.greendao.GroupDao
import com.chat.androidclient.im.ChatIM
import com.chat.androidclient.mvvm.model.Constant
import com.chat.androidclient.mvvm.model.Contact
import com.chat.androidclient.mvvm.model.Group
import com.chat.androidclient.mvvm.procotol.request.CreateGroupRequest
import com.chat.androidclient.mvvm.procotol.response.CreateGroupResponse
import com.chat.androidclient.mvvm.view.activity.CreateGroupActivity
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by lps on 2019/1/28 15:13.
 */
class CreateGroupVM(var view: CreateGroupActivity) : BaseViewModel() {
    val session = DaoMaster.newDevSession(view, Constant.DBNAME)
    
    fun getInputWatcher() = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            view.changeCommitBtn(s.length > 0)
        }
        
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }
    
    fun createGroup(groupName: String) {
//     创建群组的
        val request = CreateGroupRequest(groupName)
        ChatIM.instance.cmd(request)
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun createGroupEvent(event: CreateGroupResponseEvent) {
        val response = event.msg as CreateGroupResponse
        if (response.error) {
            view.showMsg("失败${response.errorInfo}")
        }
        else {
            var group = session.groupDao.queryBuilder().where(GroupDao.Properties.Name.eq("群组")).unique()
            if (group == null) {
                group = Group()
                group.name = "群组"
            }
            session.groupDao.insertOrReplace(group)
            val id = group.id
            val con = Contact()
            con.headprofile = response.icon
            con.id = response.groupId
            con.customid = id
            con.nickname = response.groupName
            session.contactDao.insert(con)
            view.finish()
        }
    }
}