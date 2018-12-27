package com.im.nettychat.executor;

import com.im.nettychat.boot.CustomStarter;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author hejianglong
 * @date 2018/12/25.
 */
public class AsyncTaskPool extends ServerThreadPool {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(CustomStarter.class);

    public final static AsyncTaskPool TASK_POOL = new AsyncTaskPool();

    private final ThreadPoolExecutor executor;

    public static void init() {
        logger.info("async task pool init ..");
    }

    private AsyncTaskPool() {
        executor = getExecutor();
    }

    public void execute(Runnable task) {
        executor.execute(task);
    }

    public Future submit(Callable task) {
        return executor.submit(task);
    }

    public void shutdown() {
        executor.shutdown();
    }

    public void shutdownNow() {
        executor.shutdownNow();
    }
}
