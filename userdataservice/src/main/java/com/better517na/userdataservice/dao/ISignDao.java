package com.better517na.userdataservice.dao;

import java.util.List;
import java.util.Map;

import com.better517na.userdataservice.model.Sign;
import com.better517na.userdataservice.model.SignRecord;
import com.better517na.userdataservice.model.VSet;

/**
 * @author zhuojiu
 * @date 2019-02-27 09:03
 */
public interface ISignDao {
    List<SignRecord> signRecord(Map m) throws Exception;

    List<VSet> selectAllVSet(String claID) throws Exception;

    boolean insertSign(Sign sign) throws Exception;

    List<VSet> selectVSet(Map m);
}
