package com.self.codemaker.controller;

import com.self.codemaker.annotation.MethodLog;
import com.self.codemaker.data.ApiResponse;
import com.self.codemaker.service.RuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huangchangjun
 * @date 2024/7/28
 */
@Slf4j
@RestController
@RequestMapping("/drools")
public class DroolsController {

    @Autowired
    RuleService ruleServiceImpl;

    @MethodLog
    @GetMapping("/nameRule")
    public ApiResponse nameRule(String name) {
        String department = ruleServiceImpl.nameRule(name);
        return ApiResponse.buildSuccess(department);
    }
}
