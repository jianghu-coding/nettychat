package com.chat.androidclient.mvvm.viewmodel

import com.chat.androidclient.event.FriendResponseEvent
import com.chat.androidclient.event.ThemeEvent
import com.chat.androidclient.greendao.DaoMaster
import com.chat.androidclient.greendao.GroupDao
import com.chat.androidclient.im.ChatIM
import com.chat.androidclient.mvvm.model.Constant
import com.chat.androidclient.mvvm.model.Friend
import com.chat.androidclient.mvvm.model.Group
import com.chat.androidclient.mvvm.procotol.GetFriendRequest
import com.chat.androidclient.mvvm.procotol.response.GetFriendResponse
import com.chat.androidclient.mvvm.view.fragment.ContactsFragment
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by lps on 2018/12/24 15:23.
 */
class ContactsVM(var view: ContactsFragment) : BaseViewModel() {
    val session = DaoMaster.newDevSession(view.activity, Constant.DBNAME)
    
    fun init() {
        // to do 从数据库或网络加载联系人
        loadFriendListFromNetWork()
        testData()
    }
    
     fun loadFriendListFromNetWork() {
        ChatIM.instance.cmd(GetFriendRequest())
        
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun ContactsResponse(event :FriendResponseEvent){
        val response = event.msg as GetFriendResponse
        var group = session.groupDao.queryBuilder().where(GroupDao.Properties.Name.eq("好友")).unique()
        if (group==null){
            group=Group()
            group.name="好友"
        }
        val groupId = session.groupDao.insertOrReplace(group)
        response.friends.forEach {
            var friend = Friend()
            friend.userId=it.id
            friend.customid=groupId
            friend.nickname=it.username
            friend.headprofile=it.icon
            friend.sign=it.desc
            session.friendDao.insertOrReplace(friend)
        }
        testData()
    }
    private fun testData() {
        val groups = session.groupDao.queryBuilder().list()
//        val group = Group()
//        group.__setDaoSession(session)
//        group.name="好友"
//        val friendid = session.groupDao.insert(group)
//        for (i in 1..10){
//           val friend = Friend()
//           friend.userId=friendid
//           friend.devicesAndState="Iphone 在线"
//           friend.nickname="测试用户啊$i"
//           session.friendDao.insert(friend)
//
//
//       }
//
        
        view.refreshData(groups)
        view.refreshComplet()
    }
    @Subscribe
    fun  themeChange(event: ThemeEvent){
        view.refreshUI()
    }
}