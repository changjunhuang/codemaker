package com.self.codemaker.constants;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author huangchangjun
 * @date 2023/4/15
 */
@Configuration
public class RedisConstants {

    @Value("${spring.redis.host}")
    public String REDIS_HOST;

    @Value("${spring.redis.post}")
    public int REDIS_PORT;

    @Value("${spring.redis.password}")
    public String REDIS_PASSWORD;

    @Value("${spring.redis.timeout}")
    public int REDIS_LOCK_EXPIRE_TIME;

    @Value("${spring.redis.lettuce.pool.max-active}")
    public int CONNECTION_POOL_SIZE;

}
