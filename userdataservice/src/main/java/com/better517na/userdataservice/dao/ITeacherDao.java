package com.better517na.userdataservice.dao;

import com.better517na.userdataservice.model.Class;
import com.better517na.userdataservice.model.Student;
import com.better517na.userdataservice.model.Teacher;

/**
 * @author zhuojiu
 * @date 2019-02-27 09:03
 */
public interface ITeacherDao {
    Teacher selectTeacher(String phone);

    boolean creatClass(Class aClass);
}
