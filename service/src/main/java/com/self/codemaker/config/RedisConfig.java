package com.self.codemaker.config;


import com.self.codemaker.constants.RedisConstants;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.support.ConnectionPoolSupport;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Slf4j
@Configuration
public class RedisConfig {

    @Autowired
    private RedisConstants redisConstants;

    @Bean(destroyMethod = "close")
    public GenericObjectPool<StatefulRedisConnection<String, String>> redisConnection(RedisClient redisClient) {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxIdle(redisConstants.CONNECTION_POOL_SIZE / 3 + 1);
        config.setMaxTotal(redisConstants.CONNECTION_POOL_SIZE);
        config.setBlockWhenExhausted(true);
        config.setMaxWaitMillis(1000);
        config.setMinEvictableIdleTimeMillis(30000);
        config.setTestWhileIdle(true);
        config.setTimeBetweenEvictionRunsMillis(600000);
        config.setNumTestsPerEvictionRun(10);
        config.setJmxEnabled(false);
        GenericObjectPool<StatefulRedisConnection<String, String>> pool = ConnectionPoolSupport
                .createGenericObjectPool(() -> redisClient.connect(), config);
        return pool;
    }

    @Bean(destroyMethod = "shutdown")
    public RedisClient redisClient() {
        RedisURI build = RedisURI.builder()
                .withHost(redisConstants.REDIS_HOST)
                .withPort(redisConstants.REDIS_PORT)
                .withPassword(redisConstants.REDIS_PASSWORD)
                .withTimeout(Duration.ofSeconds(1))
                .build();
        return RedisClient.create(build);
    }
}
