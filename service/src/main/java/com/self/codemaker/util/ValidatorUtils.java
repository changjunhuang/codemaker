package com.self.codemaker.util;

import org.apache.commons.collections.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * @author huangchangjun
 * @date 2023/4/15
 * @description 注解校验
 */
public class ValidatorUtils {


    /**
     * 获取验证结果的第一个错误消息
     *
     * @param t 校验对象
     * @return 第一个错误提示信息
     */
    public static <T> String getFirstError(T t) {
        if (t == null) {
            return "参数异常";
        }
        //验证
        List<ConstraintViolation<T>> list = validator(t);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0).getMessage();
        }
        return null;
    }

    /**
     * 验证，并已list返回
     *
     * @param t 校验对象
     * @return 校验结果
     */
    public static <T> List<ConstraintViolation<T>> validator(T t) {
        List<ConstraintViolation<T>> list = new ArrayList<>();
        if (t != null) {
            Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
            Set<ConstraintViolation<T>> violations = validator.validate(t);
            Iterator<ConstraintViolation<T>> iter = violations.iterator();
            if (iter.hasNext()) {
                ConstraintViolation<T> cv = iter.next();
                list.add(cv);
            }
        }
        return list;
    }
}
