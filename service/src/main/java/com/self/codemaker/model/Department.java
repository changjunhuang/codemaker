package com.self.codemaker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 部门表
 *
 * @author huangchangjun
 * @date 2023-4-8
 */
@Data
@Builder
@AllArgsConstructor
public class Department {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 部门名称
     */
    private String name;
}
