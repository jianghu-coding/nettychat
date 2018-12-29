package com.chat.androidclient.mvvm.viewmodel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.databinding.ObservableField
import android.graphics.BitmapFactory
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.TimeUtils
import com.chat.androidclient.R
import com.chat.androidclient.event.MessageEvent
import com.chat.androidclient.greendao.DaoMaster
import com.chat.androidclient.greendao.DaoSession
import com.chat.androidclient.mvvm.model.Constant
import com.chat.androidclient.mvvm.model.Conversation
import com.chat.androidclient.mvvm.model.MessageResponse
import com.chat.androidclient.mvvm.view.fragment.ConversationFragment
import org.greenrobot.eventbus.Subscribe

/**
 * Created by lps on 2018/12/24 15:23.
 */
class ConversationVM(var view: ConversationFragment) : BaseViewModel() {
    private var builder: NotificationCompat.Builder? = null
    private var notification: NotificationManager? = null
    val isEmpty = ObservableField(false)
    private var session: DaoSession = DaoMaster.newDevSession(view.activity, Constant.DBNAME)
    
    fun init() {
        loadConversationFormDB()
    }
    
    private fun loadConversationFormDB() {
        // load msg from db
        val conversationList = session.conversationDao.queryBuilder().list()
        view.refreshConversation(conversationList)
        isEmpty.set(conversationList.isEmpty())
        
    }
    
    @Subscribe
    fun ReciveMessage(event: MessageEvent) {
        val response = event.msg as MessageResponse
        if (response.fromUserId == SPUtils.getInstance().getLong(Constant.UserId)) {
        
        }
        else {
            //发送通知
            notification(response)
            //写入聊天消息的db
            session.messageResponseDao.insert(response)
            //更新最近会话列表的DB
            val conversation = Conversation()
            conversation.fromId = response.fromUserId
            conversation.msgcount += 1
            conversation.lastcontent = response.message
            conversation.time = TimeUtils.getFriendlyTimeSpanByNow(System.currentTimeMillis())
            session.conversationDao.insertOrReplace(conversation)
            //更新RecyclerView
            loadConversationFormDB()
            
        }
    }
    
    private fun notification(response: MessageResponse) {
        if (builder == null)
            builder = NotificationCompat.Builder(view.activity, "recivemessage")
        builder!!.setContentTitle("收到新的消息")
                .setContentText(response.message)
                .setLargeIcon(BitmapFactory.decodeResource(view.resources, R.mipmap.fsf))
                .setSmallIcon(R.mipmap.fsf)
                .setWhen(System.currentTimeMillis())
        if (notification == null)
            notification = view.activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChanel()
        }
        notification!!.notify(0, builder!!.build())
    }
    
    var channel: NotificationChannel? = null
    
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChanel() {
        if (channel == null) {
            channel = NotificationChannel("recivemessage", "process", NotificationManager.IMPORTANCE_LOW)
            builder!!.setOnlyAlertOnce(true)
            notification!!.createNotificationChannel(channel)
        }
    }
    
    override fun destroy() {
        super.destroy()
        //to do 关闭数据库
    }
    
}