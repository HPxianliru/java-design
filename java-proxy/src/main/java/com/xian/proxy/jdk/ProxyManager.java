package com.xian.proxy.jdk;

import com.xian.proxy.jdk.impl.StudentInterfaceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Description
 * @Author: xlr
 * @Date: Created in 9:38 上午 2021/1/3
 */
public class ProxyManager {

    public void proxy() {
        StudentInterface newProxyInstance = (StudentInterface) Proxy.newProxyInstance(
                StudentInterfaceImpl.class.getClassLoader(),//类加载器
                new Class[] {StudentInterface.class}, // 要代理的接口class
                new StudentInvocationHandler()// 处理执行类
        );

        newProxyInstance.getName( "tom" );
        newProxyInstance.getNumber( 100 );
    }
}
