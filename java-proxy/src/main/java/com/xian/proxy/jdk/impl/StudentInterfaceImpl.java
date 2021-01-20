package com.xian.proxy.jdk.impl;

import com.xian.proxy.jdk.StudentInterface;
import com.xian.proxy.modul.Student;

/**
 * @Description
 * @Author: xlr
 * @Date: Created in 9:36 上午 2021/1/3
 */
public class StudentInterfaceImpl implements StudentInterface {


    @Override
    public String getName(String name) {
        return name;
    }

    @Override
    public Integer getNumber(Integer number) {
        return number;
    }
}
