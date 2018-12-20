package com.im.nettychat.config;

import com.im.nettychat.util.CollectionUtils;
import java.io.IOException;
import java.util.Properties;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
public class ConfigProperties {

    public static Properties SERVER_PROPERTIES;

    public static void initParam(String[] args) throws IOException {
        loadConfig();
        if (CollectionUtils.isNotNullOrEmpty(args)) {
            loadTerminalParam(args);
        }
    }

    private static void loadTerminalParam(String[] args) {
        LoadTerminal loadTerminal = new LoadTerminal();
        Properties loadTerminalProperties = loadTerminal.loadParam(args);
        loadTerminalProperties.keySet().forEach(key -> {
            String val = loadTerminalProperties.getProperty(String.valueOf(key));
            SERVER_PROPERTIES.setProperty(String.valueOf(key), val);
        });
    }

    private static void loadConfig() throws IOException {
        LoadProperties loadProperties = new LoadProperties();
        SERVER_PROPERTIES = loadProperties.loadParam(new String[]{"server.properties"});
    }
}
