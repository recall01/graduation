package com.better517na.userdataservice.mapping;

import org.apache.ibatis.annotations.Mapper;
import com.better517na.userdataservice.model.Sign;

@Mapper
public interface SignMapping {

    boolean insertSign(Sign sign);
}
