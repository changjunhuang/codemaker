package com.self.codemaker.controller;

import com.self.codemaker.annotation.MethodLog;
import com.self.codemaker.component.TransactionalComponent;
import com.self.codemaker.data.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author huangchangjun
 * @date
 */
@Slf4j
@RestController
@RequestMapping("/transactional")
public class TransactionalController {

    @Resource
    private TransactionalComponent transactionalComponent;

    @MethodLog
    @GetMapping("/insert")
    public ApiResponse<Void> insert() {
        transactionalComponent.insert();
        return ApiResponse.buildSuccess();
    }
}
