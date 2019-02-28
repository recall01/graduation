package com.better517na.userdataservice.mapping;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.better517na.userdataservice.model.VSet;

@Mapper
public interface VSetMapping {
    List<VSet> selectVSet(String claID);
}
