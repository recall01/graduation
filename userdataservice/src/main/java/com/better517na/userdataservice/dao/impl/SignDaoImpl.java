package com.better517na.userdataservice.dao.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.better517na.userdataservice.dao.ISignDao;
import com.better517na.userdataservice.mapping.SignMapping;
import com.better517na.userdataservice.mapping.SignRecordMapping;
import com.better517na.userdataservice.mapping.VSetMapping;
import com.better517na.userdataservice.model.Sign;
import com.better517na.userdataservice.model.SignRecord;
import com.better517na.userdataservice.model.VSet;

/**
 * @author zhuojiu
 * @description:
 * @date 2019-02-27 08:52
 */
@Component
public class SignDaoImpl implements ISignDao {
    @Resource
    private SignRecordMapping signRecordMapping;
    @Resource
    private VSetMapping vSetMapping;
    @Resource
    private SignMapping signMapping;

    @Override
    public List<SignRecord> signRecord(Map m) throws Exception{
        return signRecordMapping.selectSignRecord(m);
    }

    @Override
    public List<VSet> selectAllVSet(String claID) throws Exception {
        return vSetMapping.selectAllVSet(claID);
    }

    @Override
    public boolean insertSign(Sign sign) throws Exception{
        return signMapping.insertSign(sign);
    }

    @Override
    public List<VSet> selectVSet(Map m) {
        return vSetMapping.selectVSet(m);
    }
}
