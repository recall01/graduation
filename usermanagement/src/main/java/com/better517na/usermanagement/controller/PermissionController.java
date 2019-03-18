package com.better517na.usermanagement.controller;

import com.better517na.usermanagement.model.Student;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.better517na.usermanagement.model.Response;
import com.better517na.usermanagement.service.IPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import static com.better517na.usermanagement.utils.Constant.RESPONSE_FALSE;

/**
 * @author zhuojiu
 * @description:
 * @date 2019-02-26 17:11
 */
@Api(tags = "权限管理接口")
@RestController
@RequestMapping(value = "/permission")
public class PermissionController {
    @Autowired
    private IPermissionService permissionService;
    @HystrixCommand(fallbackMethod = "queryAllFallback")
    @ApiOperation(value = "查询所有权限接口",notes = "查询所有权限")
    @RequestMapping(value = "/queryAllPermissions",method = RequestMethod.GET)
    public Response queryAllPermissions(){
        return permissionService.queryAllPermissions();
    }


    //回调函数
    private Response queryAllFallback(){
        return getFallback();
    }

    private Response getFallback(){
        //记录日志
        Response response = new Response<>();
        response.setStatus(RESPONSE_FALSE);
        response.setMsg("服务器请求忙，请稍后再试。");
        return response;
    }

}
