package com.chat.androidclient.network;

import com.chat.androidclient.mvvm.view.IView;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NetworkScheduler {
    public static ObservableTransformer compose() {
        return upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    public static LifecycleTransformer bindToLifecycle(IView view) {
        if (view instanceof RxAppCompatActivity) {
            return ((RxAppCompatActivity) view).bindUntilEvent(ActivityEvent.DESTROY);
        } else if (view instanceof RxFragment) {
            return ((RxFragment) view).bindToLifecycle();
        }
        throw new IllegalArgumentException("view isn't activity or fragment");
    }
}
