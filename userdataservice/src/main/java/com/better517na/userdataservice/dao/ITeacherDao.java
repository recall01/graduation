package com.better517na.userdataservice.dao;

import com.better517na.userdataservice.model.Class;
import com.better517na.userdataservice.model.Student;
import com.better517na.userdataservice.model.Teacher;
import com.better517na.userdataservice.model.VO.ClassVO;
import com.better517na.userdataservice.model.VSet;

import java.util.List;

/**
 * @author zhuojiu
 * @date 2019-02-27 09:03
 */
public interface ITeacherDao {
    Teacher selectTeacher(String phone);

    boolean creatClass(Class aClass);

    boolean changeClass(Class cla);

    Class getClassByTeaNumber(String teaNumber);

    int getCountByClaId(String claId);

    boolean creatSet(VSet set);

    List<VSet> getVSetsByTeaNumber(String teaNumber);
}
