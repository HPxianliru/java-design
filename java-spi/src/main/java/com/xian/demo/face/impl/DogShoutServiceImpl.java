package com.xian.demo.face.impl;

import com.xian.demo.face.ShoutService;

/**
 * @Description
 * @Author: xlr
 * @Date: Created in 8:48 下午 2020/12/26
 */
public class DogShoutServiceImpl implements ShoutService {

    @Override
    public void shout() {
        System.out.println("wang!");
    }

    @Override
    public String getCode() {
        return "dog";
    }
}
