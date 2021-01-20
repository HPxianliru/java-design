package com.xian.dynamic;


/**
 * @Description
 * @Author: xlr
 * @Date: Created in 9:27 上午 2021/1/3
 */
public class Main {


    public static void main(String[] args) {
        DynamicThreadPool pool = DynamicThreadPool.getDynamicThreadPool();
        pool.setCorePoolSize( 11 );
    }
}
