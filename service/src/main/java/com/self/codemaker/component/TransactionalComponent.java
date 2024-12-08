package com.self.codemaker.component;

import com.self.codemaker.dao.DepartmentMapper;
import com.self.codemaker.model.Department;
import com.self.codemaker.thread.TaskManager;
import com.self.codemaker.thread.ThreadPoolManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Supplier;

/**
 * @author huangchangjun
 * @date
 */
@Slf4j
@Component
public class TransactionalComponent {

    @Resource
    private AsynComponent asynComponent;
    @Resource
    private TaskManager taskManager;
    @Resource
    private DepartmentMapper departmentMapper;

    @Resource
    private TransactionTemplate transactionTemplate;

    private static int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private ThreadPoolExecutor executor = ThreadPoolManager.createThreadPoolExecutor("pool", AVAILABLE_PROCESSORS * 2, AVAILABLE_PROCESSORS * 6, 1000);


    @Transactional(rollbackFor = Exception.class)
    public void insert() {
        List<Supplier<Void>> taskList = new ArrayList<>();
        try {
            log.info("start");
            Department department = new Department();
            department.setName("start");
            departmentMapper.insert(department);

            for (int i = 0; i < 10; i++) {
                taskList.add(insertData(i));
            }
            taskManager.submites(taskList, 1000 * 60);

            log.info("异步线程执行完成，现在开始执行主线程");
            department.setName("end");
            departmentMapper.insert(department);
            log.info("finsh");
        } catch (Exception e) {
            log.error("insertData error", e);
            throw new RuntimeException(e);
        }
    }

    public Supplier<Void> insertData(int i) {
        return () -> {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                    try {
                        Department department = new Department();
                        department.setName("" + i);
                        departmentMapper.insert(department);

                        if (i % 2 == 0) {
                            int temp = 1 / 0;
                        }
                    } catch (Exception e) {
                        log.error(" insertData error, index = {}", i, e);
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(e);
                    }
                }
            });
            return null;
        };
    }
}
