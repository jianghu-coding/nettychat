package com.im.nettychat.proxy;

import com.im.nettychat.cache.RedisPool;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import redis.clients.jedis.Jedis;
import java.lang.reflect.Method;
import static com.im.nettychat.cache.LocalRSession.LOCAL_JEDIS;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/22 下午12:04
 */
public class CglibJedisInterceptor implements MethodInterceptor {

    private final static CglibJedisInterceptor cglibJedisInterceptor = new CglibJedisInterceptor();

    private CglibJedisInterceptor() {}

    public static Object getCglibProxy(Class<?> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(cglibJedisInterceptor);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        if (Object.class == method.getDeclaringClass()) {
            return methodProxy.invokeSuper(obj, args);
        }
        // 线程初次调用redis查询的时候回将jedis封装到ThreadLocal
        // 便于其它地方使用和使用结束后统一关闭
        Jedis jedis = LOCAL_JEDIS.get();
        if (jedis == null) {
            System.out.println("设置jedis了啊我曹");
            LOCAL_JEDIS.set(RedisPool.getJedis());
        }
        return methodProxy.invokeSuper(obj, args);
    }
}
