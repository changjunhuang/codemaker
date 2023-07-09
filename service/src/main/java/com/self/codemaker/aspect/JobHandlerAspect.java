package com.self.codemaker.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.MDC;
import org.springframework.data.redis.core.script.DigestUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * aop实现 traceId设置 (定时任务，MQ，多线程)
 *
 * @author huangchangjun
 * @date 2023/07/09
 */
@Slf4j
@Aspect
@Component
public class JobHandlerAspect {

    @Before("execution (* com.self.codemaker.mq..*.*(..)) || execution (* com.self.codemaker.thread..*.*(..))")
    public void beforeFetchDataMethod(JoinPoint joinPoint) {
        //往slf4j的MDC中添加traceId
        if (StringUtils.isEmpty(MDC.get("traceId"))) {
            String traceId = DigestUtils.sha1DigestAsHex(UUID.randomUUID().toString());
            MDC.put("traceId", String.join("-", "traceId", traceId));
            log.info("method :{} 添加 traceId", joinPoint.getSignature().getDeclaringTypeName());
        }
    }

    @After("execution (* com.self.codemaker.mq..*.*(..)) || execution (* com.self.codemaker.thread..*.*(..))")
    public void afterProcessDataMethod(JoinPoint joinPoint) {
        MDC.put("traceId", null);
    }
}
