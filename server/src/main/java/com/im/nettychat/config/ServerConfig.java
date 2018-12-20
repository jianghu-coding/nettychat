package com.im.nettychat.config;

import static com.im.nettychat.config.ConfigProperties.SERVER_PROPERTIES;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
public class ServerConfig {

    private final static String PORT = "port";

    public static int getPort() {
        return Integer.parseInt(SERVER_PROPERTIES.getProperty(PORT));
    }
}
