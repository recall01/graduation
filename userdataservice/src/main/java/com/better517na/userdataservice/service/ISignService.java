package com.better517na.userdataservice.service;

import com.better517na.userdataservice.model.Response;
import com.better517na.userdataservice.model.Sign;
import com.better517na.userdataservice.model.Student;

/**
 * @author zhuojiu
 * @date 2019-02-27 09:00
 */
public interface ISignService {
    Response signRecord(String id, String time);

    Response selectAllVSet(String claID);

    Response insertSign(Sign sign);

    Response selectVSet(String claID, String stuId);

    Response queryVSetBySetId(String setID);
}
