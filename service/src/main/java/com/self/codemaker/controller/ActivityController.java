package com.self.codemaker.controller;

import com.self.codemaker.annotation.MethodLog;
import com.self.codemaker.data.ApiResponse;
import com.self.codemaker.enums.ApiResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author huangchangjun
 * @date 2024/7/16
 */
@Slf4j
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;


    /**
     * 部署流程
     *
     * @return
     */
    @MethodLog
    @PostMapping("/deployProcess")
    public ApiResponse deployProcess() {
        Deployment deployment = processEngine.getRepositoryService() //与流程定义和部署对象相关的Service
                .createDeployment() //创建一个部署对象
                .name("请假流程") //添加部署名称
                .category("测试流程类别")
                .addClasspathResource("bpmn/myProcess.bpmn20.xml") //从classpath的资源中加载，一次只能加载一个文件
                .deploy();//完成部署
        log.info("部署ID：{}", deployment.getId());
        log.info("部署名称: {}", deployment.getName());
        return ApiResponse.buildSuccess();
    }

    /**
     * 启动流程
     *
     * @return
     */
    @MethodLog
    @PostMapping("/startProcess")
    public ApiResponse startProcess(String processKey) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey);
        log.info("流程定义id：{}", processInstance.getProcessDefinitionId());
        log.info("流程实例id：{}", processInstance.getId());
        log.info("当前活动id：{}", processInstance.getActivityId());
        return ApiResponse.buildSuccess();
    }

    /**
     * 完成个人任务
     *
     * @return
     */
    @MethodLog
    @PostMapping("/completeTask")
    public ApiResponse completeTask(String processKey, String operator) {
        //  查询该用户是否有该任务
        Task task = taskService.createTaskQuery().processDefinitionKey(processKey).taskAssignee(operator).singleResult();
        if (task == null) {
            log.info("{} 没有任务", operator);
            return ApiResponse.buildFailure(ApiResponseCode.DATA_EMPTY);
        }

        log.info("{} 完成任务,{} - {}", operator, task.getName(), task.getId());
        taskService.complete(task.getId());
        return ApiResponse.buildSuccess();
    }

    /**
     * 删除流程定义
     *
     * @return
     */
    @MethodLog
    @PostMapping("/delProcessDefinition")
    public ApiResponse delProcessDefinition(String deploymentId) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 通过流程引擎获取repositoryService
        RepositoryService repositoryService = processEngine
                .getRepositoryService();

        repositoryService.deleteDeployment(deploymentId);
        //设置true 级联删除流程定义，即使该流程有流程实例启动也可以删除
        //repositoryService.deleteDeployment(deploymentId, true);
        return ApiResponse.buildSuccess();
    }

    /**
     * 查询流程实例审批历史信息
     *
     * @return
     */
    @MethodLog
    @GetMapping("/findHistoryInfo")
    public ApiResponse<List<HistoricActivityInstance>> findHistoryInfo(String deploymentId) {
        //  获取 actinst表的查询对象
        HistoricActivityInstanceQuery instanceQuery = historyService.createHistoricActivityInstanceQuery();
        //  查询 actinst表，条件：根据 InstanceId 查询
//        instanceQuery.processInstanceId("2501");
        //  查询 actinst表，条件：根据 DefinitionId 查询
        instanceQuery.processDefinitionId(deploymentId);
        //  增加排序操作,orderByHistoricActivityInstanceStartTime 根据开始时间排序 asc 升序
        instanceQuery.orderByHistoricActivityInstanceStartTime().asc();
        //  查询所有内容
        List<HistoricActivityInstance> activityInstanceList = instanceQuery.list();
        return ApiResponse.buildSuccess(activityInstanceList);
    }

    /**
     * 查询流程定义
     *
     * @return
     */
    @MethodLog
    @GetMapping("/queryProcessDefinition")
    public ApiResponse<List<ProcessDefinition>> getActivity() {
        List<ProcessDefinition> list = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createProcessDefinitionQuery()//创建一个流程定义查询
                /*指定查询条件,where条件*/
                //.deploymentId(deploymentId)//使用部署对象ID查询
                //.processDefinitionId(processDefinitionId)//使用流程定义ID查询
                //.processDefinitionKey(processDefinitionKey)//使用流程定义的KEY查询
                //.processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询

                /*排序*/
                .orderByProcessDefinitionVersion().asc()//按照版本的升序排列
                //.orderByProcessDefinitionName().desc()//按照流程定义的名称降序排列

                .list();//返回一个集合列表，封装流程定义
//                .singleResult();//返回唯一结果集
        return ApiResponse.buildSuccess(list);
    }
}
