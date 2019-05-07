package com.better517na.usermanagement.business;

import com.better517na.usermanagement.model.Response;
import com.better517na.usermanagement.model.Student;

/**
 * @author zhuojiu
 * @date 2019-02-21 18:18
 */
public interface IStudentBusiness {
    Response registStudent(Student student);
    Response selectStudent(String account,String password);
    Response changeInfo(Student student);

    Response queryClassByClaID(String claID);

    Response changePassword(String phone, String password);

    Response queryStudentsByClaID(String claID);

    Response queryClassByStuNumber(String stuNumber);

    Response removeStudent(String stuNumber);

    Response addStudent(String stuNumber, String claID);

    Response queryClassByDynamic(String dynamic);
}
