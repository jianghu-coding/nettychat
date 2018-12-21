package com.chat.androidclient.network

class ApiException(throwable: Throwable, var msg: String) : Exception(throwable) {

}