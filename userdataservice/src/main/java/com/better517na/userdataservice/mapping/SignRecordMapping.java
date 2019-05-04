package com.better517na.userdataservice.mapping;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.better517na.userdataservice.model.SignRecord;

@Mapper
public interface SignRecordMapping {
    List<SignRecord> selectSignRecord(Map m);

    List<SignRecord> selectRecordsBySetId(String setId);
}
