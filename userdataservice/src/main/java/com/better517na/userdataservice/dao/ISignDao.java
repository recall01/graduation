package com.better517na.userdataservice.dao;

import java.util.List;
import java.util.Map;
import com.better517na.userdataservice.model.SignRecord;
import com.better517na.userdataservice.model.VSet;

/**
 * @author zhuojiu
 * @date 2019-02-27 09:03
 */
public interface ISignDao {
    List<SignRecord> signRecord(Map m) throws Exception;

    List<VSet> selectVSet(String claID) throws Exception;
}
