package com.chat.androidclient.serialize.impl

import com.alibaba.fastjson.JSON
import com.chat.androidclient.serialize.Serializer
import com.chat.androidclient.serialize.SerializerAlgorithm


class JSONSerializer :Serializer {
    override fun getSerializerAlgorithm()= SerializerAlgorithm.JSON
    
    override fun serialize(`object`: Any): ByteArray {

        return JSON.toJSONBytes(`object`)
    }

    override fun <T> deserialize(clazz: Class<T>, bytes: ByteArray): T {

        return JSON.parseObject(bytes, clazz)
    }
}
