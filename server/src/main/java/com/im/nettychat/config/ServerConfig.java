package com.im.nettychat.config;

import static com.im.nettychat.config.load.ConfigProperties.SERVER_PROPERTIES;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
public class ServerConfig {

    private final static String PORT = "port";

    private final static String CORE_THREAD = "thread.num";

    private final static String REDIS_HOST = "redis.host";

    private final static String REDIS_PORT = "redis.port";

    public static int getPort() {
        return Integer.parseInt(SERVER_PROPERTIES.getProperty(PORT));
    }

    public static int getCoreThread() {
        return Integer.parseInt(SERVER_PROPERTIES.getProperty(CORE_THREAD, "6"));
    }

    public static String getRedisHost() {
        return SERVER_PROPERTIES.getProperty(REDIS_HOST, "127.0.0.1");
    }

    public static int getRedisPort() {
        return Integer.parseInt(SERVER_PROPERTIES.getProperty(REDIS_PORT, "3306"));
    }

}
