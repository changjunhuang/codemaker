package com.self.codemaker.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author huangchangjun
 * @date 2023-4-9
 */
@Getter
@AllArgsConstructor
public enum DepartmentEnum {
    IT("it", "it"),
    SALE("seal", "销售");

    private String code;
    private String desc;

    /**
     * 判断值是否满足枚举中的值
     *
     * @param code
     * @return
     */
    public static boolean validation(String code) {
        for (DepartmentEnum departmentEnum : DepartmentEnum.values()) {
            if (Objects.equals(departmentEnum.getCode(), code)) {
                return true;
            }
        }
        return false;
    }
}
