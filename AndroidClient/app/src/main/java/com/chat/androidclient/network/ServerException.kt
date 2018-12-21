package com.chat.androidclient.network

class ServerException(var code: String, var msg: String) : RuntimeException()