package com.chat.androidclient.mvvm.viewmodel

import android.app.Fragment
import android.databinding.ObservableField
import android.os.Bundle
import android.os.Handler
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.chat.androidclient.App
import com.chat.androidclient.R
import com.chat.androidclient.event.LoginResponseEvent
import com.chat.androidclient.im.ChatIM
import com.chat.androidclient.mvvm.model.Constant
import com.chat.androidclient.mvvm.model.LoginRequest
import com.chat.androidclient.mvvm.procotol.response.LoginResponse
import com.chat.androidclient.mvvm.view.activity.MainActivity
import com.chat.androidclient.mvvm.view.fragment.ContactsFragment
import com.chat.androidclient.mvvm.view.fragment.ConversationFragment
import com.chat.androidclient.mvvm.view.fragment.DynamicFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.HashMap

/**
 * Created by lps on 2018/12/24 11:14.
 */
class MainVM(val view: MainActivity) : BaseViewModel() {
    var conversationSelected: ObservableField<Boolean> = ObservableField(false)
    var contactsSelected: ObservableField<Boolean> = ObservableField(false)
    val dynamicSelected: ObservableField<Boolean> = ObservableField(false)
    val titleText: ObservableField<String> = ObservableField("")
    
    var fragments: HashMap<String, Fragment> = hashMapOf()
    var currentFragment: Fragment? = null
    
    
    fun connect(){
        //这里可以根据是否在线。在重连。不然从login进来。会重复调用登陆
        if (!App.CONNECT) {
            Handler().postDelayed({ ChatIM.instance.cmd(LoginRequest(SPUtils.getInstance().getString(Constant.LoginUserName), SPUtils.getInstance().getString(Constant.LoginUserName))) }, 500)
        }else{
            view.hideLoading()
        }
    }
    /**
     * 登陆的结果
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun loginResponse(event: LoginResponseEvent) {
        val response = event.msg as LoginResponse
        if (response.error){
            view.showMsg(""+response.errorInfo)
            view.showLoading()
        }else{
            App.CONNECT=true
            SPUtils.getInstance().put(Constant.id,response.userId!!)
            view.hideLoading()
        }
    }
    /**
     * 选中会话
     */
    fun checkConversation() {
        if (conversationSelected.get() as Boolean) return
        conversationSelected.set(true)
        contactsSelected.set(false)
        dynamicSelected.set(false)
        titleText.set("消息")
        switchFragment(ConversationFragment::class.java)
    }
    
    /**
     * 选中联系人
     */
    fun checkContacts() {
        if (contactsSelected.get() as Boolean) return
        conversationSelected.set(false)
        contactsSelected.set(true)
        dynamicSelected.set(false)
        titleText.set("联系人")
        switchFragment(ContactsFragment::class.java)
    }
    
    /**
     * 选中动态
     */
    fun checkDynamic() {
        if (dynamicSelected.get() as Boolean) return
        conversationSelected.set(false)
        contactsSelected.set(false)
        dynamicSelected.set(true)
        titleText.set("动态")
        switchFragment(DynamicFragment::class.java)
    }
    
    private fun switchFragment(clazz: Class<*>) {
        val name = clazz.name
        val fragment: Fragment
        if (fragments.containsKey(name)) {
            fragment = fragments[name]!!
        }
        else {
            fragment = Class.forName(name).newInstance() as Fragment
            fragments[name] = fragment
        }
        if (currentFragment == null) {
            view.fragmentManager
                    .beginTransaction()
                    .add(R.id.main_content, fragment)
                    .commit()
            currentFragment = fragment
            return
        }
        if (currentFragment!=fragment) {
            if (fragment.isAdded) {
                view.fragmentManager
                        .beginTransaction()
                        .show(fragment)
                        .hide(currentFragment)
                        .commit()
            }
            else {
                view.fragmentManager
                        .beginTransaction()
                        .add(R.id.main_content, fragment)
                        .hide(currentFragment)
                        .commit()
            }
            currentFragment = fragment
        }
    
    }
 
    
}