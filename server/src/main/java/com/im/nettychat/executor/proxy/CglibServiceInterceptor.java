/*
 * Project: com.im.nettychat.executor.proxy
 * 
 * File Created at 2018/12/21
 * 
 * Copyright 2018 CMCC Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * ZYHY Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license.
 */
package com.im.nettychat.executor.proxy;

import com.im.nettychat.executor.ThreadPoolService;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author hejianglong
 * @Desc
 * @date 2018/12/21 下午10:16
 */
public class CglibServiceInterceptor implements MethodInterceptor {

    private static CglibServiceInterceptor cglibServiceInterceptor = new CglibServiceInterceptor();

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
        Future future = ThreadPoolService.submit(new Callable() {
            @Override
            public Object call() throws Exception {
                Object result = null;
                try {
                    result = methodProxy.invokeSuper(obj, args);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                return result;
            }
        });
        return future.get();
    }
}
