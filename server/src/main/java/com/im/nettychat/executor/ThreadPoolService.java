package com.im.nettychat.executor;

import com.im.nettychat.config.ServerConfig;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
public class ThreadPoolService {

    private final static ExecutorService executorService = Executors.newFixedThreadPool(ServerConfig.getCoreThread());

    public static void execute(Runnable task) {
        executorService.execute(task);
    }

    public static Future submit(Callable task) {
        return executorService.submit(task);
    }

    public static ExecutorService getExecutorService() {
        return executorService;
    }
}
