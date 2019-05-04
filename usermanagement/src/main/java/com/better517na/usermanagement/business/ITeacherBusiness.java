package com.better517na.usermanagement.business;

import com.better517na.usermanagement.model.Class;
import com.better517na.usermanagement.model.Response;
import com.better517na.usermanagement.model.VSet;

/**
 * @author zhuojiu
 * @date 2019-02-21 18:18
 */
public interface ITeacherBusiness {

    Response loginTeacher(String account);

    Response creatClass(Class aClass);

    Response changeClass(Class cla);

    Response getClassNByTeaNumber(String teaNumber);

    Response creatSet(VSet set);

    Response getVSetsByTeaNumber(String teaNumber);
}
