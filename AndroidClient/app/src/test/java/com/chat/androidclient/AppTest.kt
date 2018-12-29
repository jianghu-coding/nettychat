package com.chat.androidclient

import org.junit.Assert.*
import org.junit.Test

/**
 * Created by lps on 2018/12/29 14:49.
 */
class AppTest{
    @Test
    fun  test(){
        var listOf = mutableListOf<Int>()
    listOf= listOf.asReversed<Int>()
        listOf.forEach {
            println(it)
        }
    }
}