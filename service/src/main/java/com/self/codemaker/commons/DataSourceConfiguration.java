package com.self.codemaker.commons;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huangchangjun
 * @date 2023/5/4
 */
@Slf4j
@Configuration
public class DataSourceConfiguration {

    @Bean(name = "mysqlMaster")
    @ConfigurationProperties(prefix = "spring.datasource.mysql-master")
    public HikariConfig masterConfig() {
        return new HikariConfig();
    }

    @Bean(name = "mysqlSlave")
    @ConfigurationProperties(prefix = "spring.datasource.mysql-slave")
    public HikariConfig slaveConfig() {
        return new HikariConfig();
    }

    @Bean("dataSource")
    public DataSource dataSource() {
        //  将多数据源放入hashmap中
        Map<Object, Object> dataSourceMap = new HashMap<>();
        DataSource masterDataSource = new HikariDataSource(masterConfig());
        DataSource slaveDataSource = new HikariDataSource(slaveConfig());
        dataSourceMap.put("master", masterDataSource);
        dataSourceMap.put("slave", slaveDataSource);

        //  设置动态数据源
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(dataSourceMap);
        //  设置默认数据源
        dataSource.setDefaultTargetDataSource(masterDataSource);
        log.info("数据源初始化完成 {}", dataSourceMap);

        //  记录多数据源id
        DynamicDataSourceContextHolder.setDataSourceIds("master");
        DynamicDataSourceContextHolder.setDataSourceIds("slave");
        return dataSource;
    }
}
