package com.xian.demo.face.impl;

import com.xian.demo.face.ShoutService;

/**
 * @Description
 * @Author: xlr
 * @Date: Created in 8:45 下午 2020/12/26
 */
public class CatShoutServiceImpl implements ShoutService {

    @Override
    public void shout() {
        System.out.println("miao~");
    }

    @Override
    public String getCode() {
        return "cat";
    }
}
