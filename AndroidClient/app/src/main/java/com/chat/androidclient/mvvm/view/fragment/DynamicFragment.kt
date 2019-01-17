package com.chat.androidclient.mvvm.view.fragment


import android.util.TypedValue
import com.chat.androidclient.R
import com.chat.androidclient.databinding.FragmentDynamicBinding
import com.chat.androidclient.mvvm.viewmodel.DynamicVM

/**
 * @author lps
 * 会话Fragment
 *
 */
class DynamicFragment : BaseFragment<FragmentDynamicBinding, DynamicVM>() {
    override fun getViewModel() = DynamicVM(this)
    
    override fun getLayoutRes() = R.layout.fragment_dynamic
    
    fun refreshUI() {
        val bgcolor = TypedValue()
        val bggraycolor = TypedValue()
        val tvcolor = TypedValue()
        activity.theme.resolveAttribute(R.attr.ui_background, bgcolor, true)
        activity.theme.resolveAttribute(R.attr.ui_gray_background, bggraycolor, true)
        activity.theme.resolveAttribute(R.attr.tv_color, tvcolor, true)
        mDataBinding.rootView.setBackgroundResource(bggraycolor.resourceId)
        mDataBinding.frSearch.setBackgroundResource(bgcolor.resourceId)
        mDataBinding.llItem.setBackgroundResource(bgcolor.resourceId)
        mDataBinding.tvQzone.setTextColor(resources.getColor(tvcolor.resourceId))
        mDataBinding.tvNear.setTextColor(resources.getColor(tvcolor.resourceId))
        mDataBinding.tvInterest.setTextColor(resources.getColor(tvcolor.resourceId))
        val searchbgId = TypedValue()
        activity.theme.resolveAttribute(R.attr.search_bg, searchbgId, true)
        mDataBinding.reSearch.setBackgroundResource(searchbgId.resourceId)
        
        val searchtvcolor = TypedValue()
        activity.theme.resolveAttribute(R.attr.search_tv_color, searchtvcolor, true)
        mDataBinding.tvSearch.setTextColor(resources.getColor(searchtvcolor.resourceId))
        val searchiv = TypedValue()
        activity.theme.resolveAttribute(R.attr.search_icon, searchiv, true)
        mDataBinding.ivSearch.setImageResource(searchiv.resourceId)
    }
}
