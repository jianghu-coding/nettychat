package com.chat.androidclient.serialize


import com.chat.androidclient.serialize.impl.JSONSerializer

interface Serializer {

    /**
     * 序列化算法
     * @return
     */
    val serializerAlgorithm: Byte

    /**
     * java 对象转换成二进制
     */
    fun serialize(`object`: Any): ByteArray

    /**
     * 二进制转换成 java 对象
     */
    fun <T> deserialize(clazz: Class<T>, bytes: ByteArray): T

    companion object {

        val DEFAULT: Serializer = JSONSerializer()
    }

}
