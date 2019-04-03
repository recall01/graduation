package com.better517na.userdataservice.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.better517na.userdataservice.mapping.ClassMapping;
import com.better517na.userdataservice.model.Class;
import org.springframework.stereotype.Component;
import com.better517na.userdataservice.dao.IStudentDao;
import com.better517na.userdataservice.mapping.StudentsMapping;
import com.better517na.userdataservice.mapping.VStudentsMapping;
import com.better517na.userdataservice.model.Student;

/**
 * @author zhuojiu
 * @description:
 * @date 2019-02-27 08:52
 */
@Component
public class StudentDaoImpl implements IStudentDao {
    @Resource
    StudentsMapping studentsMapping;
    @Resource
    private VStudentsMapping vStudentsMapping;
    @Resource
    private ClassMapping classMapping;

    @Override
    public boolean registStudent(Student student) throws Exception{
        return studentsMapping.insertStudent(student);
    }

    @Override
    public Student selectStudent(String account, String password) throws Exception {
        if(account!=null&&!"".equals(account)&&password!=null&&!"".equals(password)){
            Map m = new HashMap();
            m.put("account",account);
            m.put("password",password);
            return vStudentsMapping.selectStudent(m);
        }else {
            return null;
        }
    }

    @Override
    public boolean changeInfo(Student student) throws Exception {
        return studentsMapping.changeInfo(student);
    }

    @Override
    public Class queryClassByClaID(String claID) throws Exception {
        return classMapping.queryClassByClaID(claID);
    }

    @Override
    public boolean changePassword(String phone, String password) throws Exception {
        if(phone!=null&&!"".equals(phone)&&password!=null&&!"".equals(password)){
            Map m = new HashMap();
            m.put("phone",phone);
            m.put("password",password);
            return studentsMapping.changePassword(m);
        }else {
            return false;
        }
    }

    @Override
    public List<Student> queryStudentsByClaID(String claID) {
        return vStudentsMapping.queryStudentsByClaID(claID);
    }

    @Override
    public Class queryClassByStuNumber(String stuNumber) throws Exception {
        return classMapping.queryClassByStuNumber(stuNumber);
    }

    @Override
    public boolean removeStudent(String stuNumber) throws Exception {
        return studentsMapping.removeStudent(stuNumber);
    }

    @Override
    public boolean addStudent(String stuNumber, String claID) throws Exception {
        if(stuNumber!=null&&!"".equals(stuNumber)&&claID!=null&&!"".equals(claID)){
            Map m = new HashMap();
            m.put("stuNumber",stuNumber);
            m.put("claID",claID);
            return studentsMapping.changeClaID(m);
        }else {
            return false;
        }
    }
}
