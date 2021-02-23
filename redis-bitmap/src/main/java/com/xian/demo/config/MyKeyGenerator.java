package com.xian.demo.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKey;

import java.lang.reflect.Method;
import java.util.Map;

public class MyKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        if (params.length == 0) {
            return SimpleKey.EMPTY;
        }
        Object param = params[0];
        // 参数为map自定义key=类名+方法名+map的key-value值
        if (param instanceof Map) {
            StringBuilder builder = new StringBuilder();
            // 分隔符
            String sp = ".";
            builder.append(target.getClass().getSimpleName()).append(sp);
            builder.append(method.getName()).append(sp);
            Map<String, Object> map = (Map<String, Object>) param;
            if (map.isEmpty()) {
                return builder.toString();
            }
            for (String key : map.keySet()) {
                builder.append(key).append("-").append(map.get(key)).append(sp);
            }
            return builder.toString();
        }
        return new SimpleKey(params);
    }

}
