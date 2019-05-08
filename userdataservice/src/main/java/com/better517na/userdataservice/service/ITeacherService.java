package com.better517na.userdataservice.service;

import com.better517na.userdataservice.model.Class;
import com.better517na.userdataservice.model.Response;
import com.better517na.userdataservice.model.VSet;

/**
 * @author zhuojiu
 * @date 2019-02-27 09:00
 */
public interface ITeacherService {
    Response selectTeacher(String phone);

    Response creatClass(Class aClass);

    Response changeClass(Class cla);

    Response getClassNByTeaNumber(String teaNumber);

    Response creatSet(VSet set);

    Response getVSetsByTeaNumber(String teaNumber);

    Response getTeacherByTeaId(String teaId);
}
