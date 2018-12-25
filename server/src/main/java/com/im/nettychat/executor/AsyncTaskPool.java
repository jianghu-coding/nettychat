package com.im.nettychat.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author hejianglong
 * @date 2018/12/25.
 */
public class AsyncTaskPool extends ServerThreadPool {

    public final static AsyncTaskPool TASK_POOL = new AsyncTaskPool();

    private AsyncTaskPool() { }

    public void execute(Runnable task) {
        getExecutor().execute(task);
    }

    public Future submit(Callable task) {
        return getExecutor().submit(task);
    }

    public void shutdown() {
        getExecutor().shutdown();
    }

    public void shutdownNow() {
        getExecutor().shutdownNow();
    }
}
