package com.self.codemaker.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * mybatsi-plus 属性自动填充，只有使用 mybatis-plus的api方法时才会生效
 *
 * @author huangchangjun
 * @date 2023-4-10
 */
@Configuration
public class MybatisObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        //新增时填充的字段
        setFieldValByName("createTime", new Date(), metaObject);
        setFieldValByName("updateTime", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //更新时 需要填充字段
        setFieldValByName("updateTime", new Date(), metaObject);
    }
}
