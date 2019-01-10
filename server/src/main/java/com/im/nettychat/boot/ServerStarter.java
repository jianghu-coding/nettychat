package com.im.nettychat.boot;

import com.im.nettychat.cache.RedisBootstrap;
import com.im.nettychat.config.db.DBUtil;
import com.im.nettychat.config.load.ConfigProperties;
import com.im.nettychat.executor.AsyncTaskPool;
import java.io.IOException;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
public class ServerStarter {

    public static void main(String[] args) throws IOException, InterruptedException {
        init(args);
        start();
    }

    private static void start() throws InterruptedException {
        new Thread(new CustomStarter()).start();
        // 监听处理http服务
        // new Thread(new HttpStarter()).start();
    }

    private static void init(String[] args) throws IOException {
        ConfigProperties.initParam(args);
        RedisBootstrap.init();
        AsyncTaskPool.init();
        DBUtil.init();
    }
}
