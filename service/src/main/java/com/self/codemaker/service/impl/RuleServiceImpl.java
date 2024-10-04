package com.self.codemaker.service.impl;

import com.self.codemaker.model.Department;
import com.self.codemaker.service.RuleService;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author huangchangjun
 * @date 2024/7/28
 */
@Slf4j
@Service
public class RuleServiceImpl implements RuleService {

    @Autowired
    private KieContainer kieContainer;

    @Autowired
    private KieBase kieBase;

    @Override
    public String nameRule(String name) {
        Department department = new Department();
        department.setName(name);
        //  开启会话。填写入参，则是从容器中获取指定名称的会话
        KieSession kieSession = kieContainer.newKieSession();
        //  设置规则匹配结果返回对象，全局变量，名称和类型必须和规则文件中定义的全局变量名称对应
        //  全局变量可以在本session中使用，在规则文件中使用同名名称去引用
//        kieSession.setGlobal("department", department);
        //  设置规则匹配参数
        kieSession.insert(department);
        //  触发规则
        kieSession.fireAllRules();
        //  终止会话
        kieSession.dispose();
        return department.getName();
    }

    @Override
    public void rule() {
        KieSession kieSession = kieBase.newKieSession();
        kieSession.fireAllRules();
        kieSession.dispose();
    }
}
