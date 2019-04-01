package com.better517na.userdataservice.dao.impl;

import com.better517na.userdataservice.dao.IStudentDao;
import com.better517na.userdataservice.dao.ITeacherDao;
import com.better517na.userdataservice.mapping.ClassMapping;
import com.better517na.userdataservice.mapping.StudentsMapping;
import com.better517na.userdataservice.mapping.TeacherMapping;
import com.better517na.userdataservice.mapping.VStudentsMapping;
import com.better517na.userdataservice.model.Class;
import com.better517na.userdataservice.model.Student;
import com.better517na.userdataservice.model.Teacher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhuojiu
 * @description:
 * @date 2019-02-27 08:52
 */
@Component
public class TeacherDaoImpl implements ITeacherDao {
    @Resource
    TeacherMapping teacherMapping;
    @Resource
    private VStudentsMapping vStudentsMapping;
    @Resource
    private ClassMapping classMapping;

    @Override
    public Teacher selectTeacher(String phone) {
        return teacherMapping.selectTeacher(phone);
    }
}
