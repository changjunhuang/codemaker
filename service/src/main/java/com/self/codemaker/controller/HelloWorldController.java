package com.self.codemaker.controller;

import com.self.codemaker.service.impl.RedisServiceImpl;
import com.self.codemaker.util.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author huangchangjun
 * @date 2023-4-8
 */
@Slf4j
@RestController
@RequestMapping("/codemaker")
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

    @GetMapping("/getBean")
    public String getBean(String beanName) {
        log.info("通过spring 容器map获取bean实例! beanName = {}", beanName);
        RedisServiceImpl redisServiceImpl = (RedisServiceImpl) SpringBeanUtil.getBean(beanName);
        if (Objects.nonNull(redisServiceImpl)) {
            log.info("已经通过applicationContext获取了 bean!");
        }
        return "getBean end";
    }
}
