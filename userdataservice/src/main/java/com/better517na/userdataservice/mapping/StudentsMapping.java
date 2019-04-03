package com.better517na.userdataservice.mapping;

import org.apache.ibatis.annotations.Mapper;
import com.better517na.userdataservice.model.Student;

import java.util.Map;

@Mapper
public interface StudentsMapping {

    boolean insertStudent(Student student);

    boolean changeInfo(Student student);

    boolean changePassword(Map m);

    boolean removeStudent(String stuNumber);

    boolean changeClaID(Map m);
}
