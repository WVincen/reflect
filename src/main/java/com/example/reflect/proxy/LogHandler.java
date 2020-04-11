package com.example.reflect.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author vincent
 */
public class LogHandler implements InvocationHandler {
    // 被代理的对象，实际的方法执行者
    Object target;

    public LogHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object invoke = method.invoke(target, args);
        after();
        return invoke;
    }

    // 在执行方法之前执行
    private void before() {
        System.out.println(String.format("log start time [%s] ", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date())));
    }

    // 在执行方法之后执行
    private void after() {
        System.out.println(String.format("log end time [%s] ", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date())));
    }
}
