package com.better517na.usermanagement.service;

import com.better517na.usermanagement.model.Response;
import com.better517na.usermanagement.model.Sign;
import com.better517na.usermanagement.model.Student;

public interface ISignService {
    Response signRecord(String id, String time);

    Response selectVSet(String claID);
}
