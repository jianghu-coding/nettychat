package com.chat.androidclient.mvvm.view.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.chat.androidclient.R
import com.chat.androidclient.databinding.FragmentContactsBinding
import com.chat.androidclient.databinding.FragmentConversationBinding
import com.chat.androidclient.mvvm.viewmodel.ContactsVM
import com.chat.androidclient.mvvm.viewmodel.ConversationVM

/**
 * @author lps
 * 会话Fragment
 *
 */
class ContactsFragment : BaseFragment<FragmentContactsBinding, ContactsVM>() {
    override fun getViewModel()= ContactsVM()
    
    override fun getLayoutRes()=R.layout.fragment_contacts
    override fun init() {
        var str="图文混排测排测试图文混排测试图文混排测试图文混排测试图" +
                "图文混排测排测试图文混排测试图文混图"
        var bilder=SpannableString(str);
        val drawable = resources.getDrawable(R.drawable.fhi)
        drawable.setBounds(0,0,drawable.minimumWidth,drawable.minimumHeight)
        val imageSpan = ImageSpan(drawable)
        bilder.setSpan(imageSpan,str.length-1,str.length,ImageSpan.ALIGN_BASELINE)
        mDataBinding.tv.setText(bilder)
    }
    
 
    
}
