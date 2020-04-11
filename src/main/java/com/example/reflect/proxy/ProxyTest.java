package com.example.reflect.proxy;

import org.junit.Test;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;

/**
 * @author vincent
 */
public class ProxyTest {

    @Test
    public void staticTest() {
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        UserServiceStaticProxy proxy = new UserServiceStaticProxy(userServiceImpl);
        proxy.selectById();
        proxy.update();
    }


    @Test
    public void jdkProxy() {
        // 1. 创建被代理的对象，UserService接口的实现类
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        // 2. 获取对应的 ClassLoader
        ClassLoader classLoader = userServiceImpl.getClass().getClassLoader();
        // 3. 获取所有接口的 Class，这里的 UserServiceImpl 只实现了一个接口 UserService
        Class<?>[] interfaces = userServiceImpl.getClass().getInterfaces();
        // 4. 创建一个将传给代理类的调用请求处理器，处理所有的代理对象上的方法调用
        LogHandler logHandler = new LogHandler(userServiceImpl);

        //5. 根据上面提供的信息，创建代理对象在这个过程中，JDK 会通过根据传入的参数信息动态地在内存中创建和 .class 文件等同的字节码，
        //   然后根据相应的字节码转换成对应的 class，然后调用 newInstance() 创建代理实例。
        UserService proxy = (UserService) Proxy.newProxyInstance(classLoader, interfaces, logHandler);

        proxy.selectById();
        proxy.update();

        // 保存 JDK 动态代理生成的代理类，类名保存为 UserServiceProxy
        generateClassFile(userServiceImpl.getClass(), "UserServiceProxy");
    }

    /**
     * 将根据类信息动态生成的二进制字节码保存到硬盘中，默认的是 clazz 目录下
     * params: clazz 需要生成动态代理类的类
     * proxyName: 为动态生成的代理类的名称
     */
    public static void generateClassFile(Class clazz, String proxyName) {
        // 根据类信息和提供的代理类名称，生成字节码
        byte[] classFile = ProxyGenerator.generateProxyClass(proxyName, clazz.getInterfaces());
        String paths = clazz.getResource(".").getPath();
        System.out.println(paths);
        FileOutputStream out = null;
        try {
            //保留到硬盘中
            out = new FileOutputStream(paths + proxyName + ".class");
            out.write(classFile);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}










