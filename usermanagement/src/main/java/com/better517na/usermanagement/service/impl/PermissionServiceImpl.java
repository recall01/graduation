package com.better517na.usermanagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.better517na.usermanagement.business.IPermissionBusiness;
import com.better517na.usermanagement.model.LogProducer;
import com.better517na.usermanagement.model.Response;
import com.better517na.usermanagement.service.IPermissionService;

@Service
public class PermissionServiceImpl implements IPermissionService {
    @Autowired
    private IPermissionBusiness permissionBusiness;

    @Autowired
    private LogProducer logProducer;

    @Override
    public Response queryAllPermissions() {
        return permissionBusiness.queryAllPermissions();
    }
}
