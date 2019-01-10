package com.im.nettychat.cache;

import io.netty.util.concurrent.FastThreadLocal;
import org.apache.ibatis.session.SqlSession;
import redis.clients.jedis.Jedis;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/22 下午12:40
 */
public class LocalRSession {

    // 这个本地线程池是放在自己开启的线程池中使用 外部使用无法获得
    public static final FastThreadLocal<Jedis> LOCAL_JEDIS = new FastThreadLocal();

    public static final FastThreadLocal<SqlSession> LOCAL_SESSIONS = new FastThreadLocal();
}
