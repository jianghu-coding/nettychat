package com.im.nettychat.config;

import com.im.nettychat.util.CollectionUtils;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
public class LoadTerminal implements LoadResource {

    @Override
    public Properties loadParam(String[] args) {
        Properties properties = new Properties();
        if (CollectionUtils.isNullOrEmpty(args)) {
            return properties;
        }
        Arrays.asList(args).forEach(arg -> {
            String[] params = arg.split(";");
            Arrays.asList(params).forEach(paramArr -> {
                String[] param = paramArr.split(":");
                properties.setProperty(param[0], param[1]);
            });
        });
        return properties;
    }
}
