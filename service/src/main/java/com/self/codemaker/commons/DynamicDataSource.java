package com.self.codemaker.commons;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author huangchangjun
 * @date 2023/5/4
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    /**
     * 取得一个字符串
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSourceType();
    }
}
