package com.better517na.userdataservice.mapping;

import com.better517na.userdataservice.model.Class;
import com.better517na.userdataservice.model.VO.ClassVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClassMapping {

    Class queryClassByClaID(String claID);

    Class queryClassByStuNumber(String stuNumber);

    boolean insertClass(Class aClass);

    boolean updateClass(Class cla);

    Class selectClass(String teaNumber);
}
