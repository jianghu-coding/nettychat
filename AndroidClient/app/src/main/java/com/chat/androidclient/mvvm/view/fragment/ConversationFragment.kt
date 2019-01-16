package com.chat.androidclient.mvvm.view.fragment


import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.RecycledViewPool
import android.util.TypedValue
import android.view.View
import com.blankj.utilcode.util.BarUtils
import com.chat.androidclient.R
import com.chat.androidclient.adapter.ConversationListAdapter
import com.chat.androidclient.databinding.FragmentConversationBinding
import com.chat.androidclient.event.ReConnectEvent
import com.chat.androidclient.mvvm.model.Conversation
import com.chat.androidclient.mvvm.viewmodel.ConversationVM
import org.greenrobot.eventbus.EventBus
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 * @author lps
 * 会话Fragment
 *
 */
class ConversationFragment : BaseFragment<FragmentConversationBinding, ConversationVM>() {
    override fun getViewModel() = ConversationVM(this)
    lateinit var adapter: ConversationListAdapter
    override fun getLayoutRes() = R.layout.fragment_conversation
    override fun init() {
        initRecyclerView()
        mDataBinding.vm = mVM
        mDataBinding.refreshlayout.setOnRefreshListener {
            mVM.init()
            EventBus.getDefault().post(ReConnectEvent())
        }
        mVM.init()
    }
    
    private fun initRecyclerView() {
        adapter = ConversationListAdapter(activity)
        mDataBinding.messageRecyclerView.layoutManager = LinearLayoutManager(activity)
        mDataBinding.messageRecyclerView.adapter = adapter
    }
    
    fun refreshConversation(conversations: List<Conversation>) {
        adapter.setData(conversations)
        mDataBinding.refreshlayout.finishRefresh()
    }
    
    fun refreshConversation(conversation: Conversation) {
        adapter.addMessage(conversation)
    }
    
    fun refreshUI() {
        val childCount = mDataBinding.messageRecyclerView.childCount
        val bgcolor = TypedValue()
        val divcolor = TypedValue()
        activity.theme.resolveAttribute(R.attr.ui_background, bgcolor, true)
        activity.theme.resolveAttribute(R.attr.div_color, divcolor, true)
        mDataBinding.refreshlayout.setBackgroundColor(resources.getColor(bgcolor.resourceId))
        for (i in 0 until childCount) {
            val view = mDataBinding.messageRecyclerView.getChildAt(i)
            view.setBackgroundColor(resources.getColor(bgcolor.resourceId))
            view.findViewById<View>(R.id.divview).setBackgroundColor(resources.getColor(divcolor.resourceId))
        }
        val searchbgId = TypedValue()
        activity.theme.resolveAttribute(R.attr.search_bg, searchbgId, true)
        mDataBinding.reConversationSearch.setBackgroundResource(searchbgId.resourceId)
        
        val searchtvcolor = TypedValue()
        activity.theme.resolveAttribute(R.attr.search_tv_color, searchtvcolor, true)
        mDataBinding.tvSearch.setTextColor(resources.getColor(searchtvcolor.resourceId))
        val searchiv = TypedValue()
        activity.theme.resolveAttribute(R.attr.search_icon, searchiv, true)
        mDataBinding.ivSearch.setImageResource(searchiv.resourceId)
        //让 RecyclerView 缓存在 Pool 中的 Item 失效
//     夜间模式相关实现来自  https://www.jianshu.com/p/3b55e84742e5
        //那么，如果是ListView，要怎么做呢？这里的思路是通过反射拿到 AbsListView 类中的 RecycleBin 对象，然后同样再用反射去调用 clear 方法
        var recyclerViewClass: Class<RecyclerView> = RecyclerView::class.java
        try {
            var declaredField: Field = recyclerViewClass.getDeclaredField("mRecycler")
            declaredField.setAccessible(true);
            var declaredMethod: Method = Class.forName(RecyclerView.Recycler::class.java.getName()).getDeclaredMethod("clear", *arrayOfNulls<Class<*>>(0) as Array<Class<*>>);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(declaredField.get(mDataBinding.messageRecyclerView), listOf(0));
            var recycledViewPool = mDataBinding.messageRecyclerView.getRecycledViewPool()
            recycledViewPool.clear()
            
        }
        catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }
        catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }
        catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        
    }
    
}
