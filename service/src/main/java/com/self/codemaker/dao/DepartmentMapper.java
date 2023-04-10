package com.self.codemaker.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.self.codemaker.model.Department;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author huangchangjun
 * @date 2023-4-8
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    String TABLE_NAME = "department";

    String COLUMNS = "name, create_time, update_time";

    String ALL_COLUMNS = "id," + COLUMNS;

    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "name", property = "name")
    })
    @Select("select * from " + TABLE_NAME + " where name = #{name}")
    List<Department>  selectListByName(@Param("name")String name);

    @Override
    @Insert("insert into " + TABLE_NAME + " (" + COLUMNS + ") VALUES(#{name}, #{createTime}, #{updateTime})")
    int insert(Department department);
}
