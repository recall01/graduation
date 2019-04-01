package com.better517na.usermanagement.service;

import com.better517na.usermanagement.model.Response;
import com.better517na.usermanagement.model.Student;

public interface ITeacherService {

    Response loginTeacher(String account, String code);
}
