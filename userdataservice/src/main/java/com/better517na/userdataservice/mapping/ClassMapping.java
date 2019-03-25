package com.better517na.userdataservice.mapping;

import com.better517na.userdataservice.model.Class;
import com.better517na.userdataservice.model.Sign;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClassMapping {

    Class queryClassByClaID(String claID);
}
