package com.better517na.userdataservice.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.better517na.userdataservice.dao.ISignDao;
import com.better517na.userdataservice.model.Response;
import com.better517na.userdataservice.model.Sign;
import com.better517na.userdataservice.model.SignRecord;
import com.better517na.userdataservice.model.VSet;
import com.better517na.userdataservice.service.IPermissionService;
import com.better517na.userdataservice.service.ISignService;
import static com.better517na.userdataservice.utils.Constant.RESPONSE_FALSE;
import static com.better517na.userdataservice.utils.Constant.RESPONSE_SUCCESS;

/**
 * @author zhuojiu
 * @description:
 * @date 2019-02-27 08:58
 */
@Service
public class PermissionServiceImpl implements IPermissionService {
    @Autowired
    ISignDao signDao;

    @Override
    public Response queryAllPermissions() {
        return null;
    }
}
