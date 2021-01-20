package com.xian.proxy;

import com.xian.proxy.cglib.StudentFacadeCglib;
import com.xian.proxy.jdk.ProxyManager;
import com.xian.proxy.modul.Student;

/**
 * @Description
 * @Author: xlr
 * @Date: Created in 9:27 上午 2021/1/3
 */
public class Main {


    public static void main(String[] args) {
        ProxyManager manager = new ProxyManager();
        manager.proxy();

        Student student = new Student();
        student.setName( "jike" );
        StudentFacadeCglib cglib=new StudentFacadeCglib();
        Student studentCglib=(Student)cglib.getInstance(student);
        studentCglib.getName();
    }
}
