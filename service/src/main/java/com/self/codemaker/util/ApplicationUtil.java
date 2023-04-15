package com.self.codemaker.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author huangchangjun
 * @date 2023/4/15
 */
@Slf4j
@Service
public class ApplicationUtil implements InitializingBean {
    private static final Properties APP_PROPERTIES = PropertiesUtil.loadProperties(getRootPath() + "/config/application.properties");
    private static Map<String, String> props = null;

    @Override
    public void afterPropertiesSet() {
        load(APP_PROPERTIES);
    }

    /**
     * 初始化的配置项目
     *
     * @param defaultProps
     */
    public static void load(Properties defaultProps) {
        if (null == props) {
            props = new ConcurrentHashMap<>();
            convertProperties(defaultProps);
        } else {
            convertProperties(defaultProps);
        }
    }

    public static void convertProperties(Properties defaultProps) {
        Enumeration<?> propertyNames = defaultProps.propertyNames();
        while (propertyNames.hasMoreElements()) {
            String propertyName = (String) propertyNames.nextElement();
            String propertyValue = defaultProps.getProperty(propertyName);
            if (isNotEmpty(propertyName)) {
                props.put(propertyName, propertyValue);
            }
        }
    }

    public static boolean isEmpty(String str) {
        return (str == null) || "".equals(str) || (str.trim().length() == 0);
    }


    private static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String getString(String key) {
        Object object = getProperty(key);
        if (null != object) {
            return (String) object;
        } else {
            log.warn("配置项为" + key + "的配置未添加或设置的内容为空");
            return null;
        }
    }

    public static String getString(String key, String defaultString) {
        Object object = getProperty(key);
        if (null != object) {
            return (String) object;
        } else {
            log.warn("配置项为" + key + "的配置未添加或设置的内容为空");
            return defaultString;
        }
    }

    public static Long getLong(String key) {
        Object object = getProperty(key);
        if (null != object) {
            return Long.parseLong(object.toString());
        } else {
            log.warn("配置项为" + key + "的配置未添加或设置的内容为空");
            return null;
        }
    }

    public static Long getLong(String key, long defaultLong) {
        Object object = getProperty(key);
        if (null != object) {
            return Long.parseLong(object.toString());
        } else {
            log.warn("配置项为" + key + "的配置未添加或设置的内容为空");
            return defaultLong;
        }
    }

    public static Integer getInteger(String key) {
        Object object = getProperty(key);
        if (null != object) {
            return Integer.parseInt(object.toString());
        } else {
            log.warn("配置项为" + key + "的配置未添加或设置的内容为空");
            return null;
        }
    }

    public static Integer getInteger(String key, int defaultInt) {
        Object object = getProperty(key);
        if (null != object) {
            return Integer.parseInt(object.toString());
        } else {
            log.warn("配置项为" + key + "的配置未添加或设置的内容为空");
            return defaultInt;
        }
    }

    public static String getProperty(String key) {
        if (null == props) {
            return getPropertyFromProperties(key);
        } else {
            return props.get(key);
        }
    }

    public static String getPropertyFromProperties(String key) {
        if (APP_PROPERTIES == null) {
            return null;
        }
        return APP_PROPERTIES.getProperty(key);
    }

    public static int getIntProperty(String name) {
        return getProperty(name) == null ? 0 : Integer.valueOf(getProperty(name));
    }

    public static String getRootPath() {
        String path = "";
        try {
            path = ResourceUtils.getURL("classpath:").getPath();
        } catch (Exception e) {
            log.info("根路径获取异常", e);
        }
        return path;
    }

    public static String getConfigEnvPath() {
        return getRootPath() + "/config/";
    }

    private ApplicationUtil() {
    }

}
