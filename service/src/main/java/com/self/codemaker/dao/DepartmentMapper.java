package com.self.codemaker.dao;

import com.self.codemaker.model.Department;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author huangchangjun
 * @date 2023-4-8
 */
public interface DepartmentMapper {

    String TABLE_NAME = "department";

    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "name", property = "name")
    })
    @Select("select * from " + TABLE_NAME + " where name = #{name}")
    List<Department>  selectListByName(@Param("name")String name);

}
