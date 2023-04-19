package com.self.codemaker.util;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

/**
 * 实现spring bean 获取的工具类
 *
 * @author huangchangjun
 * @date 2023/4/20
 */
public class SpringBeanUtil {

    private static ApplicationContext applicationContext;

    private static Environment environment;

    public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringBeanUtil.applicationContext == null) {
            SpringBeanUtil.applicationContext = applicationContext;
            environment = applicationContext.getEnvironment();
        }
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Environment getEnvironment() {
        return environment;
    }

    //通过name获取 Bean.
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    public static String getString(String key) {
        return environment.getProperty(key);
    }

    public static int getInt(String key) {
        return NumberUtils.toInt(environment.getProperty(key));
    }

    public static long getLong(String key) {
        return NumberUtils.toLong(environment.getProperty(key));
    }

    public static boolean getBoolean(String key) {
        return BooleanUtils.toBoolean(environment.getProperty(key));
    }
}
