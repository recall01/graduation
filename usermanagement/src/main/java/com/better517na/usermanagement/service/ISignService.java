package com.better517na.usermanagement.service;

import com.better517na.usermanagement.model.Response;
import com.better517na.usermanagement.model.Sign;
import com.better517na.usermanagement.model.Student;

import java.text.ParseException;

public interface ISignService {
    Response signRecord(String id, String time);

    Response selectAllVSet(String claID);

    Response insertSign(Sign sign);

    Response selectVSet(String claID, String stuId);

    Response queryVSetBySetId(String setID);

    Response getRecordsBySetId(String setId);
}
