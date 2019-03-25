package com.better517na.usermanagement.service;

import com.better517na.usermanagement.model.Response;
import com.better517na.usermanagement.model.Sign;
import com.better517na.usermanagement.model.Student;

public interface IStudentService {
    Response registStudent(Student student);
    Response loginStudent(String account, String password);
    Response changeInfo(Student student);

    Response queryClassByClaID(String claID);
}
