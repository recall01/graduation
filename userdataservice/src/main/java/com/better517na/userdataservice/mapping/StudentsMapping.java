package com.better517na.userdataservice.mapping;

import org.apache.ibatis.annotations.Mapper;
import com.better517na.userdataservice.model.Student;

@Mapper
public interface StudentsMapping {

    boolean insertStudent(Student student);

    boolean changeInfo(Student student);
}
