package com.self.codemaker;

import com.self.codemaker.util.SpringBeanUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author huangchangjun
 * @date 2023-04-06
 */

@EnableEurekaClient
@EnableTransactionManagement
@MapperScan({"com.self.codemaker.dao"})
@ComponentScan(basePackages = {"com.self.codemaker.*"})
//  为方便自测，禁用 默认的 Spring Security 认证
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
public class ServiceApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ServiceApplication.class, args);
        SpringBeanUtil.setApplicationContext(applicationContext);
    }
}
