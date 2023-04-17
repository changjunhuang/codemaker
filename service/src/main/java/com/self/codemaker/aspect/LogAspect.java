package com.self.codemaker.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 自定义日志注解
 *
 * @author huangchangjun
 * @date 2023-4-9
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Pointcut("@annotation(com.self.codemaker.annotation.MethodLog)")
    public void logAspect() {
    }

    @Before("logAspect()")
    public void beforeLog(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = requestAttributes.getRequest();

        //  执行方法
        String methodName = joinPoint.getSignature().getName();
        log.info("------------ Method name is " + methodName + ",begin ------------");

        //  执行时间
        log.info("Time :" + LocalDate.now() + " " + LocalTime.now());
        //  执行URL
        log.info("URL :" + httpServletRequest.getRequestURL());
        //  请求方式（post or get）
        log.info("HTTP Method :" + httpServletRequest.getMethod());
        //  controller全路径请求路径
        log.info("Class Method path:" + joinPoint.getSignature().getDeclaringTypeName() + "." + methodName);
        //  请求ip
        log.info("IP :" + httpServletRequest.getRemoteHost());
        //  请求入参
        log.info("Request params :" + JSON.toJSONString(joinPoint.getArgs()));
        log.info("------------------------------------");
    }

    @AfterReturning(value = "logAspect()", returning = "returnValue")
    public void afterLog(JoinPoint joinPoint, Object returnValue) {
        //  返回出参
        log.info("Response params :" + JSON.toJSONString(returnValue));
        //  方法执行结束
        String methodName = joinPoint.getSignature().getName();
        log.info("------------ Method name is " + methodName + ",end ------------");
    }

}
