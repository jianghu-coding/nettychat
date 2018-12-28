package com.chat.androidclient.mvvm.viewmodel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import com.blankj.utilcode.util.SPUtils
import com.chat.androidclient.R
import com.chat.androidclient.event.MessageEvent
import com.chat.androidclient.greendao.DaoMaster
import com.chat.androidclient.mvvm.model.Constant
import com.chat.androidclient.mvvm.model.MessageResponse
import com.chat.androidclient.mvvm.view.fragment.ConversationFragment
import org.greenrobot.eventbus.Subscribe

/**
 * Created by lps on 2018/12/24 15:23.
 */
class ConversationVM(var view: ConversationFragment) : BaseViewModel() {
    private var builder: NotificationCompat.Builder? = null
    private var notification:NotificationManager ?= null
    
    init {
        //todo load msg from db
    }
    
    @Subscribe
    fun ReciveMessage(event: MessageEvent) {
        val response = event.msg as MessageResponse
        if (response.fromUserId == SPUtils.getInstance().getLong(Constant.UserId)) {

        }
        else {
            //发送通知
            if (builder == null)
                builder = NotificationCompat.Builder(view.activity, "recivemessage")
            builder!!.setContentTitle("收到新的消息")
                    .setContentText(response.message)
                    .setLargeIcon(BitmapFactory.decodeResource(view.resources, R.mipmap.fsf))
                    .setSmallIcon(R.mipmap.fsf)
                    .setWhen(System.currentTimeMillis())
            if (notification==null)
             notification = view.activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createChanel()
            }
            notification!!.notify(0, builder!!.build())
            //写入db
            val session = DaoMaster.newDevSession(view.activity, Constant.DBNAME)
            session.messageResponseDao.insert(response)
            //更新RecyclerView
        }
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
}