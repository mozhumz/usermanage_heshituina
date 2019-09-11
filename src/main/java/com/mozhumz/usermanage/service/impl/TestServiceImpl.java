package com.mozhumz.usermanage.service.impl;

import com.hyj.util.web.JsonResponse;
import com.mozhumz.usermanage.mapper.IManageTestMapper;
import com.mozhumz.usermanage.model.entity.ManageTest;
import com.mozhumz.usermanage.service.ITestService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class TestServiceImpl implements ITestService {
    @Resource
    private IManageTestMapper manageTestMapper;



    @Override
    public JsonResponse add() {
        System.out.println("com.mozhumz.usermanage.service.impl.TestServiceImpl.add-xid:"+RootContext.getXID());
        ManageTest manageTest=new ManageTest();
        Date date=new Date();
        manageTest.setCreateDate(date);
        manageTest.setUpdateDate(date);
        manageTest.setStr("manageTest");
        manageTestMapper.insert(manageTest);
        return JsonResponse.success(manageTest);
    }
}
