package com.self.codemaker.aspect;

import com.self.codemaker.annotation.DataSource;
import com.self.codemaker.commons.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author huangchangjun
 * @date 2023/5/4
 */
@Slf4j
@Aspect
@Component
public class DataSourceAspect {

    @Pointcut("@annotation(com.self.codemaker.annotation.DataSource)")
    public void dataSourceAspect() {
    }

    @Before("@annotation(dataSource)")
    public void changeDataSource(JoinPoint joinPoint, DataSource dataSource) {
        String dataSourceId = dataSource.value();
        if (!DynamicDataSourceContextHolder.containDataSource(dataSourceId)) {
            DynamicDataSourceContextHolder.setDataSourceType("master");
            log.info("数据源 {} 不存在，设置成默认的 master", dataSourceId);
        } else {
            DynamicDataSourceContextHolder.setDataSourceType(dataSourceId);
            log.info("数据源切换为:{} , method = {}", dataSourceId, joinPoint.getSignature());
        }
    }

    @After("@annotation(dataSource)")
    public void restoreDataSource(JoinPoint joinPoint, DataSource dataSource) {
        DynamicDataSourceContextHolder.clearDataSourceType();
    }


}
