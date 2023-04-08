package com.self.codemaker.annotation;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;

/**
 * @author huangchangjun
 * @date  2023-04-06
 */
@Slf4j
public class EnumCheckValidator implements ConstraintValidator<EnumCheck, Object> {

    private EnumCheck enumCheck;

    @Override
    public void initialize(EnumCheck enumCheck) {
        this.enumCheck = enumCheck;
    }

    /**
     *
     * @param value value为注解的值
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        // 非空校验
        if (value == null) {
            return false;
        }
        // 校验结果
        Boolean result = Boolean.FALSE;
        // 获取参数的数据类型
        Class<?> valueClass = value.getClass();
        try {
            Method method = this.enumCheck.enumClass().getMethod(this.enumCheck.enumMethod(), valueClass);
            // 枚举校验
            result = (Boolean) method.invoke(null, value);
            if (result == null) {
                result = Boolean.FALSE;
            }
            //所有异常需要在开发测试阶段发现完毕
        } catch (Exception e) {
            log.error("EnumCheckValidator error", e);
        }
        return result;
    }
}
