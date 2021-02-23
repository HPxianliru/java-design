package com.xian.demo.support;


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
public class IServiceLoader {

    private static final Map <String, Object> SPI_SERVICE_MAP = new ConcurrentHashMap <>();

    private static final String PREFIX = "META-INF/services/";

    public static <S> List<S> load(Class<S> service) {
        Objects.requireNonNull(service, "Service interface cannot be null");
        // 获取类加载器
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        ClassLoader loader = (cl == null) ? ClassLoader.getSystemClassLoader() : cl;
        Objects.requireNonNull(loader, "class loader cannot be null");
        String uk = getUK(service, loader);
        // map中有就直接获取，否则加载
        if (SPI_SERVICE_MAP.containsKey(uk)) {
            return (List<S>) SPI_SERVICE_MAP.get(uk);
        }
        String fullName = PREFIX + service.getName();
        try {
            List<S> result = getService(service, loader, fullName);
            SPI_SERVICE_MAP.put(uk,result);
            return result;
        } catch (Exception e) {
            throw new ServiceConfigurationError("service" + service.getName() + "get sub type error." + e);
        }
    }

    private static <S> String getUK(Class<S> service, ClassLoader loader) {
        return loader.getClass().getName() + "_" + service.getName();
    }

    private static <S> List<S> getService(Class<S> service, ClassLoader loader, String fullName) throws IOException {
        List<S> result = new ArrayList <S>(16);
        // 加载器加载资源文件
        Enumeration<URL> configs = loader.getResources(fullName);
        while (configs.hasMoreElements()) {
            // 获取文件的url
            URL spiFileUrl = configs.nextElement();
            // 获取文件中的内容（实现类名）
            Set<String> classNames = loadSpiFileContent(spiFileUrl);
            // 加载每个实现类形成list返回
            classNames.forEach(each -> result.add(createService(service,loader,each)));
        }
        return result;
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
            while (eachLine != null && eachLine != "") {
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
}

