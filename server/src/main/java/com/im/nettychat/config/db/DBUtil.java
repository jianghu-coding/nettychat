package com.im.nettychat.config.db;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.InputStream;
import static com.im.nettychat.cache.LocalRSession.LOCAL_SESSIONS;

/**
 * @author hejianglong
 * @date 2019/1/10.
 */
public class DBUtil {

    private static SqlSessionFactory sqlSessionFactory = null;

    public static void init() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    /**
     * 调用需要手动 清除 LOCAL_SESSIONS 并且关闭 session
     * @param autoCommit
     * @return
     */
    public static SqlSession getSession(boolean autoCommit) {
        SqlSession sqlSession = LOCAL_SESSIONS.get();
        if (sqlSession != null) {
            return sqlSession;
        }
        sqlSession = sqlSessionFactory.openSession(autoCommit);
        LOCAL_SESSIONS.set(sqlSession);
        return sqlSession;
    }
}
