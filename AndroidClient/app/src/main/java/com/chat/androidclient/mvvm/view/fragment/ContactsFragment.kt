package com.chat.androidclient.mvvm.view.fragment


import android.content.Intent
import android.util.TypedValue
import android.view.View
import com.chat.androidclient.R
import com.chat.androidclient.adapter.FriendAdapter
import com.chat.androidclient.databinding.FragmentContactsBinding
import com.chat.androidclient.mvvm.model.Group
import com.chat.androidclient.mvvm.view.activity.CreateGroupActivity
import com.chat.androidclient.mvvm.viewmodel.ContactsVM

/**
 * @author lps
 * 联系人Fragment
 *
 */
class ContactsFragment : BaseFragment<FragmentContactsBinding, ContactsVM>() {
    override fun getViewModel() = ContactsVM(this)
    lateinit var friendAdapter: FriendAdapter
    
    override fun getLayoutRes() = R.layout.fragment_contacts
    override fun init() {
        initFriendList()
        mDataBinding.fragment=this
        mDataBinding.refreshlayout.setOnRefreshListener {
            mVM.loadFriendListFromNetWork()
            mVM.loadGroupListFromNetWork()
        }
       mVM.init()
    }
    
    private fun initFriendList() {
        friendAdapter = FriendAdapter(activity)
        mDataBinding.listviewFriends.setAdapter(friendAdapter)
        mDataBinding.listviewFriends.setGroupIndicator(null)
    }
    
    fun refreshData(datas: MutableList<Group>) {
        friendAdapter.setData(datas)
    }
    
    override fun onClick(v: View) {
        when (v.id) {
            R.id.re_new_friend -> showDevMessage()
            R.id.re_tanbaishuo -> showDevMessage()
            R.id.re_create_group->{
//                去创建群聊
                activity.startActivity(Intent(activity,CreateGroupActivity::class.java))
            }
        }
    }
    
    fun refreshComplet() {
        mDataBinding.refreshlayout.finishRefresh()
    }
    
    /**
     * 夜间模式触发更新ui
     */
    fun refreshUI() {
        val tanbaishuobg = TypedValue()
        val bgcolor = TypedValue()
        val bggraycolor = TypedValue()
        val divcolor = TypedValue()
        val tvcolor = TypedValue()
        activity.theme.resolveAttribute(R.attr.ui_background, bgcolor, true)
        activity.theme.resolveAttribute(R.attr.ui_gray_background, bggraycolor, true)
        activity.theme.resolveAttribute(R.attr.tv_color, tvcolor, true)
        activity.theme.resolveAttribute(R.attr.div_color, divcolor, true)
        activity.theme.resolveAttribute(R.attr.shape_5dp_bg, tanbaishuobg, true)
        mDataBinding.reTanbaishuo.setBackgroundResource(tanbaishuobg.resourceId)
        mDataBinding.reNewFriend.setBackgroundResource(bgcolor.resourceId)
        mDataBinding.reCreateGroup.setBackgroundResource(bgcolor.resourceId)
        mDataBinding.llRootview.setBackgroundResource(bggraycolor.resourceId)
        mDataBinding.frSearch.setBackgroundResource(bgcolor.resourceId)
        mDataBinding.tvTanbaishuo.setTextColor(resources.getColor(tvcolor.resourceId))
        mDataBinding.tvTanbaishuoContent.setTextColor(resources.getColor(tvcolor.resourceId))
        mDataBinding.tvCreateGroup.setTextColor(resources.getColor(tvcolor.resourceId))
        mDataBinding.tvNewFriend.setTextColor(resources.getColor(tvcolor.resourceId))
        mDataBinding.divCreateGroupBottom.setBackgroundResource(divcolor.resourceId)
        mDataBinding.divNewFriendBottom.setBackgroundResource(divcolor.resourceId)
        val searchbgId = TypedValue()
        activity.theme.resolveAttribute(R.attr.search_bg, searchbgId, true)
        mDataBinding.reSearch.setBackgroundResource(searchbgId.resourceId)
        
        val searchtvcolor = TypedValue()
        activity.theme.resolveAttribute(R.attr.search_tv_color, searchtvcolor, true)
        mDataBinding.tvSearch.setTextColor(resources.getColor(searchtvcolor.resourceId))
        val searchiv = TypedValue()
        activity.theme.resolveAttribute(R.attr.search_icon, searchiv, true)
        mDataBinding.ivSearch.setImageResource(searchiv.resourceId)
        
        friendAdapter.refreshUI()
    }
    
    override fun onResume() {
        super.onResume()
        mVM.loadFriendFromDB()
    }
}
