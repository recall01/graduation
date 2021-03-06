package com.better517na.userdataservice.dao;

import com.better517na.userdataservice.model.Class;
import com.better517na.userdataservice.model.Response;
import com.better517na.userdataservice.model.Student;

import java.util.List;

/**
 * @author zhuojiu
 * @date 2019-02-27 09:03
 */
public interface IStudentDao {
    boolean registStudent(Student student) throws Exception;
    Student selectStudent(String account,String password) throws Exception;
    boolean changeInfo(Student student) throws Exception;

    Class queryClassByClaID(String claID) throws Exception;

    boolean changePassword(String phone, String password) throws Exception;

    List<Student> queryStudentsByClaID(String claID) throws Exception;

    Class queryClassByStuNumber(String stuNumber) throws Exception;

    boolean removeStudent(String stuNumber) throws Exception;

    boolean addStudent(String stuNumber, String claID) throws Exception;

    Class queryClassByDynamic(String dynamic);
}
