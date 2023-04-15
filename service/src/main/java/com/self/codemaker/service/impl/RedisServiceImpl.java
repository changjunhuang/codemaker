package com.self.codemaker.service.impl;

import com.self.codemaker.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author huangchangjun
 * @date 2023/4/15
 */
@Slf4j
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public void putString(String key, String val) {
        log.info("key = {} , val = {}", key, val);
        redisTemplate.opsForValue().set(key, val);
    }

    @Override
    public String getString(String key) {
        if (redisTemplate.hasKey(key)) {
            log.info("redis中查找到信息");
            return (String) redisTemplate.opsForValue().get(key);
        } else {
            String val = "RedisTemplate";
            log.info("redis无信息，赋默认值");
            redisTemplate.opsForValue().set(key, val);
            return val;
        }
    }
}
