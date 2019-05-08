package com.better517na.userdataservice.mapping;

import com.better517na.userdataservice.model.Teacher;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeacherMapping {

    Teacher selectTeacher(String phone);

    Teacher selectTeacherByTeaId(String teaId);
}
