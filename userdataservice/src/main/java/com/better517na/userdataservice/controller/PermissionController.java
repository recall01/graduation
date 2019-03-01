package com.better517na.userdataservice.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.better517na.userdataservice.model.Response;
import com.better517na.userdataservice.model.Sign;
import com.better517na.userdataservice.service.IPermissionService;
import com.better517na.userdataservice.service.ISignService;

/**
 * @author zhuojiu
 * @description:
 * @date 2019-02-21 19:13
 */
@RestController
@RequestMapping(value = "/permission")
public class PermissionController {
    @Autowired
    private IPermissionService iPermissionService;

    @PostMapping(value = "/queryAllPermissions")
    public Response queryAllPermissions() throws Exception{
        return iPermissionService.queryAllPermissions();
    }

}
