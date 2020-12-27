package com.xian.demo.manager;

import com.xian.demo.face.ShoutService;
import com.xian.demo.support.AbstractThirdService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author: xlr
 * @Date: Created in 8:57 下午 2020/12/26
 */
public class ShoutServiceManager extends AbstractThirdService <ShoutService> {

    private static List <String> loadClassName = new ArrayList<>();

    static {
        loadClassName.add( "cat" );
        loadClassName.add( "dog" );
    }
    private ShoutServiceManager(){}

    private static class Singleton {
        static final ShoutServiceManager INSTANCE = new ShoutServiceManager();
    }

    public static ShoutServiceManager getInstance() {
        return Singleton.INSTANCE;
    }

    public static List <String> getLoadClassName(){
        return loadClassName;
    }
}
