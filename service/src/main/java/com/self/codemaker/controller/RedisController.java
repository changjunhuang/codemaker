package com.self.codemaker.controller;


import com.self.codemaker.annotation.MethodLog;
import com.self.codemaker.service.impl.RedisServiceImpl;
import com.self.codemaker.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author huangchangjun
 * @date 2023/4/15
 */
@Slf4j
@RestController
public class RedisController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisServiceImpl redisService;


    @MethodLog
    @PostMapping("/putStrToRedis")
    public String putStrToRedis(String key, String val) {
        //  redis入值
        redisService.putString(key, val);

        //  redis取值
        return redisService.getString(key);
    }

    @MethodLog
    @PostMapping("/putStrToRedisByUtil")
    public void putStrToRedisByUtil(String key, String val) {
        log.info("redis util 设置值");
        redisUtil.setString(key, val, 1000);
        log.info("成功");
        String result = redisUtil.getString(key);
        log.info("验证结果，result = {}", result);
    }
}
