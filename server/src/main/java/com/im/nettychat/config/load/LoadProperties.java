package com.im.nettychat.config.load;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
public class LoadProperties implements LoadResource {

    @Override
    public Properties loadParam(String[] args) throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = LoadProperties.class.getClassLoader().getResourceAsStream(args[0]);
            properties.load(new InputStreamReader(inputStream, "utf-8"));
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return properties;
    }
}
