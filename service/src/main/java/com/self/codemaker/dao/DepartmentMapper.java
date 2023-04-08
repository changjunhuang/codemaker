package com.self.codemaker.dao;

import ch.qos.logback.classic.db.names.TableName;
import com.self.codemaker.model.Department;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

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
