package com.im.nettychat.proxy;

import com.im.nettychat.config.ServerConfig;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import redis.clients.jedis.Jedis;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import static com.im.nettychat.cache.LocalRSession.LOCAL_JEDIS;
import static com.im.nettychat.executor.AsyncTaskPool.TASK_POOL;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/21 下午10:16
 */
public class CglibServiceInterceptor implements MethodInterceptor {

    private final static CglibServiceInterceptor cglibServiceInterceptor = new CglibServiceInterceptor();

    private CglibServiceInterceptor() {}

    public static Object getCglibProxy(Class<?> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(cglibServiceInterceptor);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        if (Object.class == method.getDeclaringClass()) {
            return methodProxy.invokeSuper(obj, args);
        }
        Future future = TASK_POOL.submit(new Callable() {
            @Override
            public Object call() throws Exception {
                Object result = null;
                try {
                    result = methodProxy.invokeSuper(obj, args);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                } finally {
                    Jedis jedis = LOCAL_JEDIS.get();
                    if (jedis != null) {
                        LOCAL_JEDIS.remove();
                        jedis.close();
                    }
                }
                return result;
            }
        });
        return future.get(ServerConfig.getServiceThreadTimeOut(), TimeUnit.SECONDS);
    }
}
