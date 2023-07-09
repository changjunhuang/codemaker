package com.self.codemaker.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.self.codemaker.annotation.DataSource;
import com.self.codemaker.annotation.MethodLog;
import com.self.codemaker.dao.DepartmentMapper;
import com.self.codemaker.data.ApiResponse;
import com.self.codemaker.model.Department;
import com.self.codemaker.vo.DepartmentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * @author huangchangjun
 * @date 2023-4-8
 */
@Slf4j
@RestController
public class DepartmentController {

    @Autowired
    private DepartmentMapper departmentMapper;

    @MethodLog
    @PostMapping("/selectDepartmentByName")
    public String selectDepartmentByName(@RequestBody @Valid DepartmentVO departmentVO) {
        if (Objects.isNull(departmentVO)) {
            log.info("入参是空");
            return null;
        }

        List<Department> departmentList = departmentMapper.selectListByName(departmentVO.getName());
        String result = JSONObject.toJSONString(departmentList);
        return result;
    }

    @MethodLog
    @PostMapping("/selectDepartmentByNameV2")
    public ApiResponse<List<Department>> selectDepartmentByNameV2(@RequestBody @Valid DepartmentVO departmentVO) {
        if (Objects.isNull(departmentVO)) {
            log.info("入参是空");
            return null;
        }
        List<Department> departmentList = departmentMapper.selectListByName(departmentVO.getName());
        return ApiResponse.buildSuccess(departmentList);
    }

    @MethodLog
    @PostMapping("/addDepartment")
    public void addDepartment(@RequestBody DepartmentVO departmentVO) {
        Department department = new Department();
        BeanUtils.copyProperties(departmentVO, department);

        int result = departmentMapper.insert(department);
        if (result == 0) {
            log.error("新记录插入失败");
        }
    }

    @MethodLog
    @DataSource("slave")
    @PostMapping("/addDepartmentToSlave")
    public void addDepartmentToSlave(@RequestBody DepartmentVO departmentVO) {
        Department department = new Department();
        BeanUtils.copyProperties(departmentVO, department);

        int result = departmentMapper.insert(department);
        if (result == 0) {
            log.error("新记录插入失败");
        }
    }

}
