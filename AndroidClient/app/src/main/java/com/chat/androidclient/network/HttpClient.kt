package com.chat.androidclient.network

import android.annotation.SuppressLint
import android.content.Context
import com.chat.androidclient.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by 李培生 on 2018/12/21 14:10.
 */
class HttpClient private constructor() {
    companion object {
        val TAG="HttpClient"
        @SuppressLint("StaticFieldLeak")
        private lateinit var context:Context
        fun init(con:Context){
            context=con
        }
        val mInstance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            HttpClient()
        }
    }
    
    init {
        val baseurl = context.getString(R.string.base_url)
        val builder = OkHttpClient.Builder()
        Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .build()
                .create(ApiService::class.java)
    }
    
}