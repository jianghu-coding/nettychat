package com.im.nettychat.domain;

import com.im.nettychat.util.Util;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author hejianglong
 * @date 2019/1/10.
 */
public class Test {

    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        User user = new User();
        user.setName("dsdsds");
        user.setUsername("dsdsds");
        user.setPassword(Util.hashPasswordAddingSalt("dsdasdsdsda"));
        //sqlSessionFactory.openSession(true).getMapper(UserMapper.class).save(user);;
        System.out.println(user);
    }
}
