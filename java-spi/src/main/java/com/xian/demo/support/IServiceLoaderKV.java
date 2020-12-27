package com.xian.demo.support;

import com.xian.demo.face.BaseThirdService;
import com.xian.demo.manager.ShoutServiceManager;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author: xlr
 * @Date: Created in 8:52 下午 2020/12/26
 */
public class IServiceLoaderKV {

    private static final String PREFIX = "META-INF/main/abc.properties";


    private static Properties loadFilterConfig(ClassLoader classLoader) throws IOException {
        Properties filterProperties = new Properties();
        ClassLoader loader = (classLoader == null) ? ClassLoader.getSystemClassLoader() : classLoader;
        for (Enumeration<URL> e = loader.getResources(PREFIX); e.hasMoreElements();) {
            URL url = e.nextElement();
            Properties property = new Properties();
            InputStream is = null;
            try {
                is = url.openStream();
                property.load(is);
            } finally {
                if (is != null) {
                    is.close();
                }
            }
            filterProperties.putAll(property);
        }
        return filterProperties;
    }



    public static <S> Map<String, S> load(Class<S> service) {
        Map <String, S> spiService = new ConcurrentHashMap <>();
        Objects.requireNonNull(service, "Service interface cannot be null");
        // 获取类加载器
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        ClassLoader loader = (cl == null) ? ClassLoader.getSystemClassLoader() : cl;
        Objects.requireNonNull(loader, "class loader cannot be null");
        String fullName = PREFIX + service.getName();
        try {
            Properties filterProperties = loadFilterConfig(loader);
            for (String s : ShoutServiceManager.getLoadClassName()) {
                for (Object o : filterProperties.keySet()) {
                    if(s.equals( String.valueOf( o ) )){
                        String className = filterProperties.getProperty( String.valueOf( o ) );
                        S s1 = createService( service, loader, className );
                        spiService.put( s, s1);
                    }
                }
            }
            return spiService;
        } catch (Exception e) {
            throw new ServiceConfigurationError("service" + service.getName() + "get sub type error." + e);
        }
    }

    private static <S> S createService(Class<S> service, ClassLoader loader, String cn) {
        Class<?> c;
        try {
            c = Class.forName(cn,false,loader);
        } catch (ClassNotFoundException x) {
            throw new ServiceConfigurationError("Provider" + cn + "not found", x);
        }
        // 如果service不是c的父类
        if (!service.isAssignableFrom(c)) {
            throw new ServiceConfigurationError("Provider" + cn + "not a subtype");
        }
        try {
            return service.cast(c.newInstance());
        }catch (Throwable x) {
            throw new ServiceConfigurationError("Provider " + cn + "could not be instantiated");
        }
    }

    private static Set <String> loadSpiFileContent(URL url) {
        Set<String> result = new HashSet <String>(16);
        InputStream in = null;
        BufferedReader r = null;
        try {
            in = url.openStream();
            r = new BufferedReader(new InputStreamReader(in, "utf-8"));
            String eachLine = r.readLine();
            if (eachLine == null) {
                return result;
            }
            while (StringUtils.isNotBlank(eachLine)) {
                int ci = eachLine.indexOf("#");
                if (ci >= 0) {
                    eachLine = eachLine.substring(0,ci);
                }
                eachLine = eachLine.trim();
                int n = eachLine.length();
                if (n != 0) {
                    if (eachLine.indexOf(' ') >= 0 || eachLine.indexOf('\t') >= 0) {
                        throw new ServiceConfigurationError("Illegal configuration-file syntax");
                    }
                    int cp = eachLine.codePointAt(0);
                    // 校验java定义符的第一个字母是否合法
                    if (!Character.isJavaIdentifierStart(cp)) {
                        throw new ServiceConfigurationError("Illegal provider-class name:" + eachLine);
                    }
                    for (int i = Character.charCount(cp); i < n; i += Character.charCount(cp)) {
                        cp = eachLine.codePointAt(i);
                        // 校验java定义符的非首字母是否合法
                        if (!Character.isJavaIdentifierPart(cp) && (cp != '.')) {
                            throw new ServiceConfigurationError("Illegal provider-class name:" + eachLine);
                        }
                    }
                }
                result.add(eachLine);
                eachLine = r.readLine();
            }
            return result;
        } catch (Exception e) {
            throw new ServiceConfigurationError("Error reading configuration file", e);
        } finally {
            try {
                if (r != null) {
                    r.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException y) {
                throw new ServiceConfigurationError("Error closing configuration file", y);
            }
        }
    }


    public static void main(String[] args) {
        Map <String, BaseThirdService> load = IServiceLoaderKV.load( BaseThirdService.class );
        System.out.println(load);
    }
}

