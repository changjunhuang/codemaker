package com.self.codemaker.component;

import com.self.codemaker.dao.DepartmentMapper;
import com.self.codemaker.model.Department;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.function.Supplier;

/**
 * @author huangchangjun
 * @date
 */
@Slf4j
@Component
public class AsynComponent {

    @Resource
    private DepartmentMapper departmentMapper;

    @Resource
    private TransactionTemplate transactionTemplate;

    public Supplier<Boolean> insertData(int i) {
        return () ->transactionTemplate.execute( new TransactionCallback<Boolean>()  {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                try {

                    Department department = new Department();
                    department.setName(""+i);
                    departmentMapper.insert(department);

                    if (i %2==0) {
                        int temp = 1 / 0;
                    }
                } catch (Exception e) {
                    log.error(" insertData error, index = {}", i, e);
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
                return null;
            }
        });
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void insertData2(int i) {
        try {
            Department department = new Department();
            department.setName("" + i);
            departmentMapper.insert(department);

            if (i == 8) {
                int temp = 1 / 0;
            }
        } catch (Exception e) {
            log.error(" insertData error, index = {}", i, e);
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
