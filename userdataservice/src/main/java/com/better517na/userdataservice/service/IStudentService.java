package com.better517na.userdataservice.service;

import com.better517na.userdataservice.model.Response;
import com.better517na.userdataservice.model.Student;

/**
 * @author zhuojiu
 * @date 2019-02-27 09:00
 */
public interface IStudentService {
    Response registStudent(Student student);
    Response selectStudent(String account,String password) throws Exception;
    Response changeInfo(Student student);

    Response queryClassByClaID(String claID);

    Response changePassword(String phone, String password);
}
