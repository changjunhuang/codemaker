package com.self.codemaker.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * properties工具类，也可以使用@Value的方式获取文件的值
 * create date 2017年3月30日 上午11:42:35
 *
 * @author huangchangjun
 */
@Slf4j
public class PropertiesUtil {

    /**
     * 加载properties文件
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public static Properties loadProperties(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            log.error("指定路径为空");
            return null;
        }

        //检测文件是否符合规则
        File configFile = new File(filePath);
        if (!configFile.exists()) {
            log.error("指定配置文件不存在：" + filePath);
            return null;
        } else if (!configFile.isFile()) {
            log.error("指定路径不是一个文件：" + filePath);
            return null;
        }

        //加载配置文件
        Properties properties = new Properties();
        InputStream stream = null;
        try {
            stream = new FileInputStream(filePath);
            properties.load(stream);
        } catch (Exception e) {
            log.error("加载文件失败：" + filePath, e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return properties;
    }

}
