package com.self.codemaker.config;

import com.self.codemaker.interceptor.ParameterInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huangchangjun
 * @date 2023-4-11
 */
@Configuration
public class MybatisConfig {
    /**
     * @Bean ： 该注解用于向容器中注入一个Bean
     * 注入Interceptor[]这个Bean
     */
    @Bean
    public Interceptor[] interceptors() {
        // 创建ParameterInterceptor这个插件
        ParameterInterceptor parameterInterceptor = new ParameterInterceptor();
        // 放入数组返回
        return new Interceptor[]{parameterInterceptor};
    }
}
