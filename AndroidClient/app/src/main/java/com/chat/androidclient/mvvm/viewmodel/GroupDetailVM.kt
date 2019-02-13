package com.chat.androidclient.mvvm.viewmodel

import android.databinding.ObservableBoolean
import com.blankj.utilcode.util.SPUtils
import com.chat.androidclient.event.GetUserGroupResponseEvent
import com.chat.androidclient.event.JoinGroupResponseEvent
import com.chat.androidclient.im.ChatIM
import com.chat.androidclient.mvvm.model.Constant
import com.chat.androidclient.mvvm.procotol.request.GetUserGroupRequest
import com.chat.androidclient.mvvm.procotol.request.JoinGroupRequest
import com.chat.androidclient.mvvm.procotol.response.GetUserGroupResponse
import com.chat.androidclient.mvvm.procotol.response.JoinGroupResponse
import com.chat.androidclient.mvvm.view.activity.GroupDetailActivity
import com.chat.androidclient.mvvm.view.custom.LoadingDialog
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by lps on 2019/2/13 11:16.
 */
class GroupDetailVM(var view: GroupDetailActivity) : BaseViewModel() {
  var id=  view.intent.getLongExtra(GroupDetailActivity.ID, 0)
    val belongGroup: ObservableBoolean = ObservableBoolean(false)
    fun init() {
        if (dialog == null) {
            dialog = LoadingDialog(view)
        }
        dialog?.show()
        //获取群组信息
        val request = GetUserGroupRequest(id)
        ChatIM.instance.cmd(request)
    }
    
    
    fun joinOrQuitGroup() {
        //判断自己是否在该群
        if (belongGroup.get()){
            //todo 属于群组做退出操作
        }else{
            //不在该群 申请加群
            dialog?.show()
            val request = JoinGroupRequest(id)
            ChatIM.instance.cmd(request)
        }
        
    }
    
@Subscribe(threadMode = ThreadMode.MAIN)
    fun getGroupDetailEvent(event: GetUserGroupResponseEvent) {
        dialog?.dismiss()
        val userGroupResponse = event.msg as GetUserGroupResponse
        checkIsInGroup(userGroupResponse)
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun joinGroupEvent(event: JoinGroupResponseEvent) {
        dialog?.dismiss()
        val joinGroupResponse = event.msg as JoinGroupResponse
        if (joinGroupResponse.error){
            view.showMsg(joinGroupResponse.errorInfo)
            return
        }
//      todo  加入成功 写入数据库
//      todo  关闭当前页面 跳转到聊天页面
    
    
    
    }
    
    
    private fun checkIsInGroup(userGroupResponse: GetUserGroupResponse) {
        for (it in userGroupResponse.userIds) {
            if (it == SPUtils.getInstance().getLong(Constant.id)) {
                belongGroup.set(true)
                return
            }
        }
        belongGroup.set(false)
    }
}