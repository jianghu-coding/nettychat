package com.chat.androidclient.network;

import com.chat.androidclient.mvvm.view.IView;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by 李培生 on 2018/10/16 15:04.
 * kotlin代码有问题。这个方法只有先用java处理了
 */
public class SubscribeHandler {
    public static <T> void toSubscribe(IView view, Observable<T> o, Observer<T> s) {
        o.onErrorResumeNext(new ErrorFunction<T>())
                .compose(NetworkScheduler.compose())
            //    .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(NetworkScheduler.bindToLifecycle(view))
                .subscribe(s);
    }
}
