package com.chat.androidclient.network

import io.reactivex.Observable
import io.reactivex.functions.Function

class ErrorFunction<T> : Function<Throwable, Observable<T>> {
    override fun apply(t: Throwable): Observable<T> {
        return Observable.error(ExceptionEngine.handleException(t))
    }
}