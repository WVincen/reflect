package com.example.reflect.proxy;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author vincent
 */
public class UserServiceStaticProxy {
    private UserService target;

    public UserServiceStaticProxy(UserService target) {
        this.target = target;
    }

    public void selectById() {
        before();
        // 这里才实际调用真实主题角色的方法
        target.selectById();
        after();
    }

    public void update() {
        before();
        // 这里才实际调用真实主题角色的方法
        target.update();
        after();
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
