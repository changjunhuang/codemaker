package com.self.codemaker.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

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
