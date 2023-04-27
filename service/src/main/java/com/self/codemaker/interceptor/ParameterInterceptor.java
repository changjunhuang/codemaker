package com.self.codemaker.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.PreparedStatement;
import java.util.Date;
import java.util.Properties;

/**
 * mybatis自定义插件，实现属性自动填充
 *
 * @author huangchangjun
 * @date 2023/4/11
 */
@Slf4j
@Intercepts({@Signature(type = ParameterHandler.class, method = "setParameters", args = {PreparedStatement.class})})
public class ParameterInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info("ParameterInterceptor execute ：" + invocation.getTarget());
        // 目标对象
        Object target = invocation.getTarget();
        // 获取目标对象中所有属性的值，因为ParameterHandler使用的是DefaultParameterHandler，因此里面的所有的属性都封装在其中
        MetaObject metaObject = SystemMetaObject.forObject(target);
        //  使用xxx.xxx.xx的方式可以层层获取属性值，这里获取的是mappedStatement中的sql类型
        SqlCommandType type = (SqlCommandType) metaObject.getValue("mappedStatement.sqlCommandType");
        // 如果是指定的查询方法
        if (SqlCommandType.INSERT.equals(type)) {
            // 设置 create time 和 update time的值
            metaObject.setValue("parameterObject.createTime", new Date());
            metaObject.setValue("parameterObject.updateTime", new Date());
        }
        if (SqlCommandType.UPDATE.equals(type)) {
            // 设置  update time的值
            metaObject.setValue("parameterObject.updateTime", new Date());
        }
        // 执行目标方法
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        // 如果没有特殊定制，直接使用Plugin这个工具类返回一个代理对象即可
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
