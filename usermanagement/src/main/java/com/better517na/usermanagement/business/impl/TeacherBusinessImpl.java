package com.better517na.usermanagement.business.impl;

import com.better517na.usermanagement.business.ITeacherBusiness;
import com.better517na.usermanagement.feignClient.UserDataFeignClient;
import com.better517na.usermanagement.model.*;
import com.better517na.usermanagement.model.Class;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhuojiu
 * @description:
 * @date 2019-02-21 18:19
 */
@Component
public class TeacherBusinessImpl implements ITeacherBusiness {

    @Autowired
    private LogProducer logProducer;
    @Autowired
    UserDataFeignClient userDataFeignClient;

    @Override
    public Response loginTeacher(String phone) {
        return userDataFeignClient.selectTeacher(phone);
    }

    @Override
    public Response creatClass(Class aClass) {
        return userDataFeignClient.creatClass(aClass);
    }

    @Override
    public Response changeClass(Class cla) {
        return userDataFeignClient.changeClass(cla);
    }

    @Override
    public Response getClassNByTeaNumber(String teaNumber) {
        return userDataFeignClient.getClassNByTeaNumber(teaNumber);
    }

    @Override
    public Response creatSet(VSet set) {
        return userDataFeignClient.creatSet(set);
    }

    @Override
    public Response getVSetsByTeaNumber(String teaNumber) {
        return userDataFeignClient.getVSetsByTeaNumber(teaNumber);
    }

}
