package com.better517na.userdataservice.service;

import com.better517na.userdataservice.model.Response;
import com.better517na.userdataservice.model.Student;

/**
 * @author zhuojiu
 * @date 2019-02-27 09:00
 */
public interface ITeacherService {
    Response selectTeacher(String phone);
}
