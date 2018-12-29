package com.chat.androidclient.mvvm.viewmodel

import android.app.Fragment
import android.databinding.ObservableField
import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.chat.androidclient.R
import com.chat.androidclient.mvvm.view.activity.MainActivity
import com.chat.androidclient.mvvm.view.fragment.ContactsFragment
import com.chat.androidclient.mvvm.view.fragment.ConversationFragment
import com.chat.androidclient.mvvm.view.fragment.DynamicFragment
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