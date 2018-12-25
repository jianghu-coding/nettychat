package com.im.nettychat.config;

import static com.im.nettychat.config.load.ConfigProperties.SERVER_PROPERTIES;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
public class ServerConfig {

    private final static String PORT = "server.port";

    private final static String HTTP_PORT = "server.http.port";

    private final static String SERVER_TIMEOUT_OPEN = "server.timeout.open";

    private final static String SERVER_READ_TIMEOUT = "server.read.timeout";

    private final static String SERVER_WRITE_TIMEOUT = "server.write.timeout";

    private final static String CORE_THREAD = "thread.num";

    private final static String THREAD_QUEUE_CAPACITY = "thread.queue.capacity";

    private final static String REDIS_HOST = "redis.host";

    private final static String REDIS_PORT = "redis.port";

    private final static String REDIS_MAX_ACTIVE = "redis.max.active";

    private final static String REDIS_MAX_IDLE = "redis.max.idle";

    private final static String REDIS_MAX_WAIT_MILLIS = "redis.max.wait.millis";

    private final static String REDIS_TIMEOUT = "redis.timeout";

    private final static String REDIS_TEST_ON_BORROW = "redis.test.on.borrow";

    private final static String REDIS_TEST_ON_RETURN = "redis.test.on.return";

    public static int getPort() {
        return Integer.parseInt(SERVER_PROPERTIES.getProperty(PORT));
    }

    public static int getHttpPort() {
        return Integer.parseInt(SERVER_PROPERTIES.getProperty(HTTP_PORT));
    }

    public static int getCoreThread() {
        return Integer.parseInt(SERVER_PROPERTIES.getProperty(CORE_THREAD, "6"));
    }

    public static int getThreadQueueCapacity() {
        return Integer.parseInt(SERVER_PROPERTIES.getProperty(THREAD_QUEUE_CAPACITY, String.valueOf(Integer.MAX_VALUE)));
    }

    public static String getRedisHost() {
        return SERVER_PROPERTIES.getProperty(REDIS_HOST, "127.0.0.1");
    }

    public static int getRedisPort() {
        return Integer.parseInt(SERVER_PROPERTIES.getProperty(REDIS_PORT, "3306"));
    }

    public static int getRedisMaxActive() {
        return Integer.parseInt(SERVER_PROPERTIES.getProperty(REDIS_MAX_ACTIVE, "500"));
    }

    public static int getRedisMaxIdle() {
        return Integer.parseInt(SERVER_PROPERTIES.getProperty(REDIS_MAX_IDLE, "100"));
    }

    public static long getRedisMaxWaitMillis() {
        return Long.valueOf(SERVER_PROPERTIES.getProperty(REDIS_MAX_WAIT_MILLIS, "5000"));
    }

    public static int getRedisTimeOut() {
        return Integer.valueOf(SERVER_PROPERTIES.getProperty(REDIS_TIMEOUT, "5000"));
    }

    public static boolean getRedisTestOnBorrow() {
        return Boolean.valueOf(SERVER_PROPERTIES.getProperty(REDIS_TEST_ON_BORROW, "true"));
    }

    public static boolean getRedisTestOnReturn() {
        return Boolean.valueOf(SERVER_PROPERTIES.getProperty(REDIS_TEST_ON_RETURN, "true"));
    }

    public static boolean getServerTimeoutOpen() {
        return Boolean.valueOf(SERVER_PROPERTIES.getProperty(SERVER_TIMEOUT_OPEN, "false"));
    }

    public static int getServerReadTimeout() {
        return Integer.valueOf(SERVER_PROPERTIES.getProperty(SERVER_READ_TIMEOUT, "5"));
    }

    public static int getServerWriteTimeout() {
        return Integer.valueOf(SERVER_PROPERTIES.getProperty(SERVER_WRITE_TIMEOUT, "5"));
    }
}
