package com.im.nettychat.executor;

import com.im.nettychat.config.ServerConfig;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hejianglong
 * @date 2018/12/25.
 */
public class ServerThreadPool {

    protected static ThreadPoolExecutor getExecutor() {
        return chooseExecutor(ServerConfig.getCoreThread(), ServerConfig.getThreadQueueCapacity());
    }

    private static ThreadPoolExecutor chooseExecutor(int coreThread, int queueCapacity) {
        // TODO - 增加其它队列线程池的实现
        return getExecutorByLinkedBlockingQueue(coreThread, queueCapacity);
    }

    private static ThreadPoolExecutor getExecutorByLinkedBlockingQueue(int coreThread, int queueCapacity) {
        // LinkedBlockingQueue, 没有最大线程数量限制, 超过核心线程=最大线程, keepAliveTime无效
        // 超过就会最大线程, 就会一直阻塞在队列中, 所以队列最好指定容量防止占用资源过高影响其它服务
        return new ThreadPoolExecutor(ServerConfig.getCoreThread(),
                coreThread,
                0,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingQueue<>(queueCapacity),
                new NamedThreadFactory(),
                new RejectedServiceHandler());
    }

    static class RejectedServiceHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

        }
    }

    static class NamedThreadFactory implements ThreadFactory {

        private static final AtomicInteger threadPoolNumber = new AtomicInteger(1);

        private final ThreadGroup threadGroup;

        private final String namePrefix;

        private final AtomicInteger threadNumber = new AtomicInteger(1);

        public NamedThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            threadGroup = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "chat-pool-" + threadPoolNumber.getAndIncrement() + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(threadGroup, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (thread.isDaemon())
                thread.setDaemon(false);
            if (thread.getPriority() != Thread.NORM_PRIORITY)
                thread.setPriority(Thread.NORM_PRIORITY);
            return thread;
        }
    }
}
