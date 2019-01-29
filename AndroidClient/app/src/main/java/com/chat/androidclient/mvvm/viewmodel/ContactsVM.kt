package com.chat.androidclient.mvvm.viewmodel

import com.chat.androidclient.event.FriendResponseEvent
import com.chat.androidclient.event.GetGroupListResponseEvent
import com.chat.androidclient.event.ThemeEvent
import com.chat.androidclient.greendao.ContactDao
import com.chat.androidclient.greendao.DaoMaster
import com.chat.androidclient.greendao.GroupDao
import com.chat.androidclient.im.ChatIM
import com.chat.androidclient.mvvm.model.Constant
import com.chat.androidclient.mvvm.model.Contact
import com.chat.androidclient.mvvm.model.Group
import com.chat.androidclient.mvvm.model.TYPE
import com.chat.androidclient.mvvm.procotol.GetFriendRequest
import com.chat.androidclient.mvvm.procotol.request.GetUserGroupListRequest
import com.chat.androidclient.mvvm.procotol.response.GetFriendResponse
import com.chat.androidclient.mvvm.procotol.response.GetUserGroupListResponse
import com.chat.androidclient.mvvm.view.fragment.ContactsFragment
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by lps on 2018/12/24 15:23.
 */
class ContactsVM(var view: ContactsFragment) : BaseViewModel() {
    val session = DaoMaster.newDevSession(view.activity, Constant.DBNAME)
    
    fun init() {
        // 从网络获取好友
        loadFriendListFromNetWork()
        //从网络获取群组
        loadGroupListFromNetWork()
    }
    
    fun loadGroupListFromNetWork() {
        ChatIM.instance.cmd(GetUserGroupListRequest())
    }
    
    fun loadFriendListFromNetWork() {
        ChatIM.instance.cmd(GetFriendRequest())
    }
    
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun ContactspersonResponse(event: FriendResponseEvent) {
        val response = event.msg as GetFriendResponse
        var group = session.groupDao.queryBuilder().where(GroupDao.Properties.Name.eq("好友")).unique()
        if (group == null) {
            group = Group()
            group.name = "好友"
        }
        session.groupDao.insertOrReplace(group)
        val groupId = group.id
        response.friends.forEach {
            var con = session.contactDao.queryBuilder().where(ContactDao.Properties.Type.eq(0), ContactDao.Properties.UserId.eq(it.id)).unique()
            if (con == null)
                con = Contact()
            con.userId = it.id
            con.customid = groupId
            con.nickname = it.username
            con.headprofile = it.icon
            con.sign = it.desc
            session.contactDao.insertOrReplace(con)
        }
        
        
        loadFriendFromDB()
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun ContactsgroupResponse(event: GetGroupListResponseEvent) {
        val response = event.msg as GetUserGroupListResponse
        var group = session.groupDao.queryBuilder().where(GroupDao.Properties.Name.eq("群组")).unique()
        if (group == null) {
            group = Group()
            group.name = "群组"
        }
        session.groupDao.insertOrReplace(group)
        val groupId = group.id
        
        response.userGroups.forEach {
            var con = session.contactDao.queryBuilder().where(ContactDao.Properties.Type.eq(1), ContactDao.Properties.UserId.eq(it.groupId)).unique()
            if (con == null)
                con = Contact()
            con.userId = it.groupId
            con.customid = groupId
            con.nickname = it.groupName
            con.headprofile = it.icon
            con.sign = it.desc
            con.type = TYPE.GROUP
            session.contactDao.insertOrReplace(con)
        }
        loadFriendFromDB()
    }
    
    
    fun loadFriendFromDB() {
        /**
         * 可能是缓存的问题。没有及时更新、
         */
        session.clear()
        val groups = session.groupDao.loadAll()
        view.refreshData(groups)
        view.refreshComplet()
    }
    
    @Subscribe
    fun themeChange(event: ThemeEvent) {
        view.refreshUI()
    }
}