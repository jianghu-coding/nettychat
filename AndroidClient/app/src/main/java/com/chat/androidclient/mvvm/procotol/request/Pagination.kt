package com.chat.androidclient.mvvm.procotol.request


/**
 * @author hejianglong
 * @date 2019/1/22.
 */
class Pagination {

    var page: Int = 0

    var size: Int = 0
        get() = if (field > 300) 300 else field

    var offset: Int = 0
}
