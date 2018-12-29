package com.chat.androidclient.mvvm.viewmodel

import com.chat.androidclient.greendao.DaoMaster
import com.chat.androidclient.mvvm.model.Constant
import com.chat.androidclient.mvvm.model.Friend
import com.chat.androidclient.mvvm.model.Group
import com.chat.androidclient.mvvm.view.fragment.ContactsFragment

/**
 * Created by lps on 2018/12/24 15:23.
 */
class ContactsVM(var view: ContactsFragment) : BaseViewModel() {
    fun init() {
        // to do 从数据库或网络加载联系人
        testData()
    }
    
    private fun testData() {
        val session = DaoMaster.newDevSession(view.activity, Constant.DBNAME)
      val groups=  session.groupDao.queryBuilder().list()
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
    }
    
}