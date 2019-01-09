package com.chat.androidclient.util

import com.blankj.utilcode.constant.TimeConstants
import java.util.*

/**
 * Created by lps on 2019/1/9 18:03.
 */
object TimeUtils{
    
    fun getTimeFriend( time:Long):String {
        // 获取当天 00:00
        val wee = getWeeOfToday()
        return if (time >= wee) {
            String.format("%tR", time)
        }
        else if (time >= wee - TimeConstants.DAY) {
            String.format("昨天%tR", time)
        }
        else {
            String.format("%tF", time)
        }
    }
    
    private fun getWeeOfToday(): Long {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }
}