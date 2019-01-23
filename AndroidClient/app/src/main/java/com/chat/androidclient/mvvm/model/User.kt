package com.chat.androidclient.mvvm.model

import java.io.Serializable

/**
 * Created by lps on 2018/12/29 17:42.
 */
class User:Serializable {
     var id: Long? = null

     var username: String? = null

     var password: String? = null

     var name: String? = null

     var icon: String? = null

     val desc: String? = null
     override fun toString(): String {
          return "User(id=$id, username=$username, password=$password, name=$name, icon=$icon, desc=$desc)"
     }
     
}
