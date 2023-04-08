package com.self.codemaker.controller;

import com.alibaba.fastjson.JSONObject;
import com.self.codemaker.dao.DepartmentMapper;
import com.self.codemaker.model.Department;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author huangchangjun
 * @date 2023-4-8
 */
@Slf4j
@RestController
public class DepartmentController {

    @Autowired
    private DepartmentMapper departmentMapper;

    @GetMapping("/selectDepartmentByName")
    private String selectDepartmentByName(@RequestParam String name) {
        if (StringUtils.isBlank(name)) {
            log.info("输入的部门名称是空");
            return null;
        }

        List<Department> departmentList = departmentMapper.selectListByName(name);
        String result = JSONObject.toJSONString(departmentList);

        log.info("查询结果为 = {}", result);
        return result;
    }
}
