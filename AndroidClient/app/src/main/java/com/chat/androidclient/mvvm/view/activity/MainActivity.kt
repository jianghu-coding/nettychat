package com.chat.androidclient.mvvm.view.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.SPUtils
import com.chat.androidclient.R
import com.chat.androidclient.databinding.ActivityMainBinding
import com.chat.androidclient.event.ThemeEvent
import com.chat.androidclient.mvvm.model.Constant
import com.chat.androidclient.mvvm.model.User
import com.chat.androidclient.mvvm.viewmodel.MainVM
import org.greenrobot.eventbus.EventBus

class MainActivity : BaseActivity<ActivityMainBinding, MainVM>() {
    override fun getLayoutRes() = R.layout.activity_main
    override fun getViewModel() = MainVM(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        //恢复上次保存的夜间模式效果
        if (SPUtils.getInstance().getBoolean(Constant.daynightmode)) {
            setTheme(R.style.NightTheme)
        }
        else {
            setTheme(R.style.DayTheme)
        }
        super.onCreate(savedInstanceState)
    }
    
    @SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(outState: Bundle?) {
    
//        super.onSaveInstanceState(outState)
    }
    override fun init() {
        mVM.connect()
        mDataBinding.vm = mVM
        mVM.checkConversation()
        mDataBinding.tvDrawNickname.text=SPUtils.getInstance().getString(Constant.LoginUserName)
        //处理状态栏效果
        initStatusBar()
        
    }
    
    private fun initStatusBar() {
        BarUtils.setStatusBarAlpha4Drawer(this, mDataBinding.drawerlayout, mDataBinding.fakebar, 0, false)
        BarUtils.addMarginTopEqualStatusBarHeight(mDataBinding.titleLayout)
    }
    
    override fun onClick(v: View) {
        when (v.id) {
            R.id.re_conversation -> {
                mVM.checkConversation()
            }
            R.id.re_contacts -> {
                mVM.checkContacts()
            }
            R.id.re_dynamic -> {
                mVM.checkDynamic()
            }
            R.id.add_conversation -> {
                startActivity(Intent(this, SearchActivity::class.java))
            }
            R.id.head -> {
                if (mDataBinding.drawerlayout.isDrawerOpen(Gravity.LEFT))
                    mDataBinding.drawerlayout.closeDrawer(Gravity.LEFT)
                else {
                    mDataBinding.drawerlayout.openDrawer(Gravity.LEFT)
                }
            }
            R.id.titlt_more -> showDevloadingMsg()
            R.id.tv_more -> showDevloadingMsg()
            R.id.iv_myhead -> {
                var user: User = User()
                user.username = SPUtils.getInstance().getString(Constant.LoginUserName)
                user.id = SPUtils.getInstance().getLong(Constant.id)
                FriendDetailActivity.launchActivity(this, user)
            }
            R.id.ll_night_mode -> {
                val oldMode = SPUtils.getInstance().getBoolean(Constant.daynightmode)
                val nowMode = !oldMode
                //更新 MainActivity ui
                if (nowMode) {
                    setTheme(R.style.NightTheme)
                }
                else {
                    setTheme(R.style.DayTheme)
                }
                refreshUI()
                EventBus.getDefault().post(ThemeEvent())
                SPUtils.getInstance().put(Constant.daynightmode, nowMode)
            }
        }
    }
    
    private fun refreshUI() {
        showAnimation()
//        下面是更新各种UI的效果
        val value_tv_color = TypedValue()
        val bgcolor = TypedValue()
        theme.resolveAttribute(R.attr.ui_background, bgcolor, true)
        theme.resolveAttribute(R.attr.tv_color, value_tv_color, true)
//        侧边栏背景色
        mDataBinding.navigationView.setBackgroundColor(resources.getColor(bgcolor.resourceId))
        mDataBinding.bottomLayout.setBackgroundColor(resources.getColor(bgcolor.resourceId))
//        侧边栏和标题栏文本颜色
        mDataBinding.tvDrawFreetraffic.setTextColor(resources.getColor(value_tv_color.resourceId))
        mDataBinding.tvDrawNigntMode.setTextColor(resources.getColor(value_tv_color.resourceId))
        mDataBinding.tvDrawQianbao.setTextColor(resources.getColor(value_tv_color.resourceId))
        mDataBinding.tvDrawSetting.setTextColor(resources.getColor(value_tv_color.resourceId))
        mDataBinding.tvDrawShoucang.setTextColor(resources.getColor(value_tv_color.resourceId))
        mDataBinding.tvDrawXiangce.setTextColor(resources.getColor(value_tv_color.resourceId))
        mDataBinding.tvDrawVip.setTextColor(resources.getColor(value_tv_color.resourceId))
        mDataBinding.tvDrawWeather.setTextColor(resources.getColor(value_tv_color.resourceId))
        mDataBinding.tvDrawWenjian.setTextColor(resources.getColor(value_tv_color.resourceId))
        mDataBinding.tvDrawZhuangban.setTextColor(resources.getColor(value_tv_color.resourceId))
        mDataBinding.tvMore.setTextColor(resources.getColor(value_tv_color.resourceId))
        mDataBinding.tvTitle.setTextColor(resources.getColor(value_tv_color.resourceId))
        mDataBinding.tvDrawTemperature.setTextColor(resources.getColor(value_tv_color.resourceId))
//        侧边栏图标
        val freetrafficid = TypedValue()
        theme.resolveAttribute(R.attr.img_freetraffic, freetrafficid, true)
        mDataBinding.ivFreetraffic.setImageResource(freetrafficid.resourceId)
        val vipid = TypedValue()
        theme.resolveAttribute(R.attr.img_svip, vipid, true)
        mDataBinding.ivSvip.setImageResource(vipid.resourceId)
        val qianbaoid = TypedValue()
        theme.resolveAttribute(R.attr.img_qianbao, qianbaoid, true)
        mDataBinding.ivQianbao.setImageResource(qianbaoid.resourceId)
        val zhuangbanid = TypedValue()
        theme.resolveAttribute(R.attr.img_zhuangban, zhuangbanid, true)
        mDataBinding.ivZhuangban.setImageResource(zhuangbanid.resourceId)
        val shoucangid = TypedValue()
        theme.resolveAttribute(R.attr.img_shoucang, shoucangid, true)
        mDataBinding.ivShoucang.setImageResource(shoucangid.resourceId)
        val xiangceid = TypedValue()
        theme.resolveAttribute(R.attr.img_xiangce, xiangceid, true)
        mDataBinding.ivXiangce.setImageResource(xiangceid.resourceId)
        val wenjianid = TypedValue()
        theme.resolveAttribute(R.attr.img_wenjian, wenjianid, true)
        mDataBinding.ivWenjian.setImageResource(wenjianid.resourceId)
        val settingid = TypedValue()
        theme.resolveAttribute(R.attr.img_setting, settingid, true)
        mDataBinding.ivSetting.setImageResource(settingid.resourceId)
        val nightmodeid = TypedValue()
        theme.resolveAttribute(R.attr.img_daynight, nightmodeid, true)
        mDataBinding.ivNigntMode.setImageResource(nightmodeid.resourceId)
    }
    
    /**
     * 更换夜间模式的动画。
     */
    private fun showAnimation() {
        val fromView = mVM.getCacheBitMapFromView(window.decorView)
        if (window.decorView is ViewGroup && fromView != null) {
            val view = View(this)
            view.setBackgroundDrawable(BitmapDrawable(resources, fromView))
            val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            (window.decorView as ViewGroup).addView(view, layoutParams)
            val animator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f)
            animator.setDuration(800)
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    (window.decorView as ViewGroup).removeView(view)
                }
            })
            animator.start()
        }
    }
    
    fun hideLoading() {
        mDataBinding.connectloading.visibility = View.GONE
    }
    
    fun showLoading() {
        mDataBinding.connectloading.visibility = View.VISIBLE
        
    }
    
    override fun onBackPressed() {
        moveTaskToBack(false)
    }
}
