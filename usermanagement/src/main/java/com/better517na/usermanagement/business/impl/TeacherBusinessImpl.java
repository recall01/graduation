package com.better517na.usermanagement.business.impl;

import com.better517na.usermanagement.business.IStudentBusiness;
import com.better517na.usermanagement.business.ITeacherBusiness;
import com.better517na.usermanagement.feignClient.UserDataFeignClient;
import com.better517na.usermanagement.model.Class;
import com.better517na.usermanagement.model.LogProducer;
import com.better517na.usermanagement.model.Response;
import com.better517na.usermanagement.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.better517na.usermanagement.utils.Constant.RESPONSE_FALSE;

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
}
