package com.better517na.usermanagement.service;

import com.better517na.usermanagement.model.Class;
import com.better517na.usermanagement.model.Response;
import com.better517na.usermanagement.model.Teacher;
import com.better517na.usermanagement.model.VSet;

public interface ITeacherService {

    Response loginTeacher(String account, String code);

    Response creatClass(Teacher teacher);

    Response changeClass(Class cla);

    Response getClassNByTeaNumber(String teaNumber);

    Response creatSet(VSet set);

    Response getVSetsByTeaNumber(String teaNumber);
}
