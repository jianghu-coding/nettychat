package com.chat.androidclient.network

import android.net.ParseException
import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException

object ExceptionEngine {
    //对应HTTP的状态码
    private val UNAUTHORIZED = 401
    private val FORBIDDEN = 403
    private val NOT_FOUND = 404


    fun handleException(e: Throwable): ApiException {
        val ex: ApiException
        if (e is HttpException) {             //HTTP错误
            ex = ApiException(e, ERROR.NETWORD_ERROR)
            when (e.code()) {
                UNAUTHORIZED, FORBIDDEN, NOT_FOUND -> ex.msg = "网络错误"  //均视为网络错误
                else -> ex.msg = "网络错误"
            }
            return ex
        } else if (e is ServerException) {    //服务器返回的错误
          
            ex = ApiException(e, e.code)
            ex.msg = e.msg
            return ex
        } else if (e is JsonParseException
                || e is JSONException
                || e is ParseException) {
            ex = ApiException(e, "解析错误")
            return ex
        } else if (e is ConnectException) {
            ex = ApiException(e, "连接失败")
            return ex
        } else {
            ex = ApiException(e, "未知错误")
         
            return ex
        }
    }
}
