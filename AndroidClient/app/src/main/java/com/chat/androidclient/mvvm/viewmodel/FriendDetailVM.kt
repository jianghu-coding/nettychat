package com.chat.androidclient.mvvm.viewmodel

import android.databinding.ObservableField
import com.chat.androidclient.event.AddFriendResponseEvent
import com.chat.androidclient.greendao.ContactDao
import com.chat.androidclient.greendao.DaoMaster
import com.chat.androidclient.greendao.GroupDao
import com.chat.androidclient.im.ChatIM
import com.chat.androidclient.mvvm.model.*
import com.chat.androidclient.mvvm.procotol.request.AddFriendRequest
import com.chat.androidclient.mvvm.view.activity.ChatActivity
import com.chat.androidclient.mvvm.view.activity.FriendDetailActivity
import com.chat.androidclient.mvvm.view.custom.LoadingDialog
import org.greenrobot.eventbus.Subscribe

/**
 * Created by lps on 2019/1/8 11:00.
 */
class FriendDetailVM(var view: FriendDetailActivity) : BaseViewModel() {
    var user: ObservableField<User> = ObservableField()
    
    fun init() {
       
        user.set( view.intent.getSerializableExtra(Constant.FRIENDDETAIL_USER_INFO) as User)
        val dao = DaoMaster.newDevSession(view, Constant.DBNAME).contactDao
        val friend = dao.queryBuilder().where(ContactDao.Properties.UserId.eq(user.get()!!.id),ContactDao.Properties.Type.eq(0)).unique()
        if (friend==null){
            view.showAddFriend()
        }else{
            view.showChat()
    
        }
    }
    
    fun addFriend() {
        if (dialog == null) {
            dialog = LoadingDialog(view)
        }
        dialog?.show()
        val request = AddFriendRequest(user.get()?.id)
        ChatIM.instance.cmd(request)
    }
    
    @Subscribe
    fun addFriendEvent(event: AddFriendResponseEvent) {
        dialog?.dismiss()
        if (event.msg.error) {
            view.showMsg(event.msg.errorInfo)
        }
        else {
            insertGroupFriendDB()
            ChatActivity.startActivity(view, user.get()!!.id!!,"我们已经是好友了，快来聊天吧！！")
            view.finish()
        }
    }
    
    /**
     * 写入数据库
     */
    private fun insertGroupFriendDB() {
        val session = DaoMaster.newDevSession(view, Constant.DBNAME)
        val friend = Contact()
        friend.userId = user.get()!!.id
        friend.nickname = user.get()?.username
        var group = session.groupDao.queryBuilder().where(GroupDao.Properties.Name.eq("好友")).unique()
        if (group == null) {
            group = Group()
            group.name = "好友"
        }
        session.groupDao.insertOrReplace(group)
        val groupId = group.id
        friend.customid = groupId
        session.contactDao.insertOrReplace(friend)
    }
    
    
    fun toChat() {
        ChatActivity.startActivity(view, user.get()!!.id!!,TYPE.PERSON)
    }
}