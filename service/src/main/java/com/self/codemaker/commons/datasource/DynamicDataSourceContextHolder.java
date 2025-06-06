package com.self.codemaker.commons.datasource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huangchangjun
 * @date 2023/5/4
 */
public class DynamicDataSourceContextHolder {

    /**
     * 使用ThreadLocal维护变量，存储当前数据源ID
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 管理所有的数据源id
     */
    private static final List<String> DATA_SOURCE_IDS = new ArrayList<>();


    public static void setDataSourceType(String dataSourceType) {
        CONTEXT_HOLDER.set(dataSourceType);
    }

    public static String getDataSourceType() {
        return CONTEXT_HOLDER.get();
    }

    public static void clearDataSourceType() {
        // ThreadLocal 变量使用完成需要手动remove，否则会导致内存泄露
        CONTEXT_HOLDER.remove();
    }

    public static void setDataSourceIds(String id) {
        DATA_SOURCE_IDS.add(id);
    }

    /**
     * 判断当前data source是否存在
     *
     * @param dataSourceId 数据源id
     * @return
     */
    public static boolean containDataSource(String dataSourceId) {
        return DATA_SOURCE_IDS.contains(dataSourceId);
    }
}
