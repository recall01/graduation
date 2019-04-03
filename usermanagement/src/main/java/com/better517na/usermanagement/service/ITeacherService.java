package com.better517na.usermanagement.service;

import com.better517na.usermanagement.model.Response;
import com.better517na.usermanagement.model.Teacher;

public interface ITeacherService {

    Response loginTeacher(String account, String code);

    Response creatClass(Teacher teacher);
}
