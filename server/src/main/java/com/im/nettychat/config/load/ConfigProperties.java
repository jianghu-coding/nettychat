package com.im.nettychat.config.load;

import com.im.nettychat.util.CollectionUtils;
import java.io.IOException;
import java.util.Properties;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
public class ConfigProperties {

    public static Properties SERVER_PROPERTIES;

    public static Properties ERROR_PROPERTIES;

    public static void initParam(String[] args) throws IOException {
        SERVER_PROPERTIES = loadConfig("server.properties");
        if (CollectionUtils.isNotNullOrEmpty(args)) {
            loadTerminalParam(args);
        }
        ERROR_PROPERTIES = loadConfig("error.properties");
    }

    private static void loadTerminalParam(String[] args) {
        LoadTerminal loadTerminal = new LoadTerminal();
        Properties loadTerminalProperties = loadTerminal.loadParam(args);
        loadTerminalProperties.keySet().forEach(key -> {
            String val = loadTerminalProperties.getProperty(String.valueOf(key));
            SERVER_PROPERTIES.setProperty(String.valueOf(key), val);
        });
    }

    private static Properties loadConfig(String resource) throws IOException {
        LoadProperties loadProperties = new LoadProperties();
        return loadProperties.loadParam(new String[]{resource});
    }
}
