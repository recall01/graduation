package com.better517na.userdataservice.mapping;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.better517na.userdataservice.model.Student;

@Mapper
public interface VStudentsMapping {
    Student selectStudent(Map m);

    List<Student> queryStudentsByClaID(String claID);
}
