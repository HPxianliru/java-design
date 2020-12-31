package com.xian.time.window;


import com.xian.time.window.common.SamplingService;

import java.util.Timer;

/**
 * @Description
 * @Author: xlr
 * @Date: Created in 10:50 下午 2020/12/31
 */
public class Main {


    public static void main(String[] args) {

        SamplingService.getInstance().boot();
        while (true) {
            for (int i = 0; i < 100 ; i++) {
                if(SamplingService.getInstance().trySampling()){
                    System.out.println( System.currentTimeMillis() );
                }
            }
        }
    }
}
