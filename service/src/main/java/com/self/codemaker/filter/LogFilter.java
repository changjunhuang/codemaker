package com.self.codemaker.filter;

import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 全局日志traceId 生成过滤器
 *
 * @author huangchangjun
 * @date 2023/7/9
 */
@Configuration
public class LogFilter extends OncePerRequestFilter implements Ordered {

    private static final String MDC_TRACE_ID = "traceId";

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //  生成traceId
        String traceId = getMdcTraceId();
        MDC.put(MDC_TRACE_ID, traceId);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        MDC.clear();
    }

    /**
     * 生产的traceId
     *
     * @return
     */
    private String getMdcTraceId() {
        long currentTime = System.nanoTime();
        return String.join("_", MDC_TRACE_ID, String.valueOf(currentTime));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
