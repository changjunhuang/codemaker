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
public class HelloWorldController {

    @GetMapping("/say/hello")
    public String sayHello() {
        log.info("输出hello!");
        return "say hello!";
    }

    @GetMapping("/say/bye")
    public String sayGoodBye() {
        log.info("输出 bye");
        return "good bye!";
    }
}
