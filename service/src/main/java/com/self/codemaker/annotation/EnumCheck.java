package com.self.codemaker.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author huangchangjun
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Constraint(validatedBy = EnumCheckValidator.class)
public @interface EnumCheck {

    /**
     * 是否可为空 默认非空
     * @return
     */
    boolean required() default true;

    /**
     * 枚举的Class
     * @return
     */
    Class<? extends Enum<?>> enumClass();

    /**
     * 枚举中的验证方法
     * @return
     */
    String enumMethod() default "validation";

    /**
     * 验证失败的文案
     * @return
     */
    String message() default "枚举验证失败";

    /**
     * 分组的内容
     * @return
     */
    Class<?>[] groups() default {};

    /**
     * 错误验证的级别
     * @return
     */
    Class<? extends Payload>[] payload() default {};

}
