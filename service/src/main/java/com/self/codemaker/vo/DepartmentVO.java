package com.self.codemaker.vo;

import com.self.codemaker.annotation.EnumCheck;
import com.self.codemaker.enums.DepartmentEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

/**
 * 部门VO
 *
 * @author huangchangjun
 * @date 2023-4-9
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentVO {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 部门名称
     */
    @NotEmpty(message = "department name为空")
    @EnumCheck(enumClass = DepartmentEnum.class, message = "department name不合法")
    private String name;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
