package com.better517na.usermanagement.business;

import com.better517na.usermanagement.model.Class;
import com.better517na.usermanagement.model.Response;
import com.better517na.usermanagement.model.Student;

/**
 * @author zhuojiu
 * @date 2019-02-21 18:18
 */
public interface ITeacherBusiness {

    Response loginTeacher(String account);

    Response creatClass(Class aClass);
}
