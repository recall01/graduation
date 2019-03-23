package com.better517na.userdataservice.mapping;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.better517na.userdataservice.model.VSet;

@Mapper
public interface VSetMapping {
    List<VSet> selectAllVSet(String claID);

    List<VSet> selectVSet(Map m);

    VSet queryVSetBySetId(String setID);
}
