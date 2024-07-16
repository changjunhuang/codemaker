package com.self.codemaker.processEngine;

import com.self.codemaker.enums.ResponseCode;
import com.self.codemaker.excption.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * @author huangchangjun
 * @date 2024/7/15
 */
@Slf4j
@Service
public class ActivityProcessEngine implements InitializingBean {

    private ProcessEngine processEngine = null;

    /**
     * 初始化activiti表，使其自动建表
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("流程引擎初始化 start");
        try {
            processEngine = ProcessEngines.getDefaultProcessEngine();
        } catch (Exception exception) {
            log.info("流程引擎初始化 fail");
            BizServiceException.throwBizException(ResponseCode.FAILURE, exception);
        } finally {
            if (processEngine != null) {
                processEngine.close();
            }
        }
        log.info("流程引擎初始化 end");
    }
}
