package com.better517na.usermanagement.service;

import com.better517na.usermanagement.model.Response;
import com.better517na.usermanagement.model.Sign;

public interface ISMSService {
    Response verifySMSCode(String url, String params);
}
