package com.xian.proxy.jdk;

import com.xian.proxy.jdk.impl.StudentInterfaceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Description
 * @Author: xlr
 * @Date: Created in 9:42 上午 2021/1/3
 */
public class StudentInvocationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理执行前");
        Object invoke = method.invoke(new StudentInterfaceImpl(), args);
        System.out.println(invoke);
        System.out.println("代理执行后");
        return invoke;
    }
}
