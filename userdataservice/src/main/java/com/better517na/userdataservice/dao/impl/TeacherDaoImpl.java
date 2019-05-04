package com.better517na.userdataservice.dao.impl;

import com.better517na.userdataservice.dao.IStudentDao;
import com.better517na.userdataservice.dao.ITeacherDao;
import com.better517na.userdataservice.mapping.*;
import com.better517na.userdataservice.model.Class;
import com.better517na.userdataservice.model.Student;
import com.better517na.userdataservice.model.Teacher;
import com.better517na.userdataservice.model.VO.ClassVO;
import com.better517na.userdataservice.model.VSet;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
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
    private StudentsMapping studentsMapping;
    @Resource
    private ClassMapping classMapping;
    @Resource
    private VSetMapping setMapping;

    @Override
    public Teacher selectTeacher(String phone) {
        return teacherMapping.selectTeacher(phone);
    }

    @Override
    public boolean creatClass(Class aClass) {
        return classMapping.insertClass(aClass);
    }

    @Override
    public boolean changeClass(Class cla) {
        return classMapping.updateClass(cla);
    }

    @Override
    public Class getClassByTeaNumber(String teaNumber) {
        return classMapping.selectClass(teaNumber);
    }

    @Override
    public int getCountByClaId(String claId) {
        return studentsMapping.selectCount(claId);
    }

    @Override
    public boolean creatSet(VSet set) {
        try {
            setMapping.insertSet(set);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<VSet> getVSetsByTeaNumber(String teaNumber) {
        return setMapping.selectVsets(teaNumber);
    }

}
