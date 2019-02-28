package com.better517na.usermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.better517na.usermanagement.model.Response;
import com.better517na.usermanagement.model.Sign;
import com.better517na.usermanagement.model.Student;
import com.better517na.usermanagement.service.ISignService;
import com.better517na.usermanagement.service.IStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author zhuojiu
 * @description:
 * @date 2019-02-26 17:21
 */
@Api(tags = "签到操作接口")
@RestController
@RequestMapping(value = "/sign")
public class SignController {
    @Autowired
    private ISignService signService;

    @ApiOperation(value = "查询签到记录接口",notes = "根据时间查询签到记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "用户账号",required = true,dataType = "String"),
            @ApiImplicitParam(name = "time",value = "查看签到记录时间",required = true,dataType = "String")
    })
    @RequestMapping(value = "record",method = RequestMethod.GET)
    public Response signRecord(@RequestParam String id, @RequestParam String time){
        return signService.signRecord(id,time);
    }

    @ApiOperation(value = "查询可签到的设置接口",notes = "根据班级编号查询可签到的设置")
    @RequestMapping(value = "queryVSet",method = RequestMethod.GET)
    public Response queryVSet(@RequestParam @ApiParam(name = "claID",value = "班级编号",required = true) String claID){
        return signService.selectVSet(claID);
    }

    @ApiOperation(value = "学生签到的接口",notes = "传递正确参数进行签到")
    @RequestMapping(value = "sign",method = RequestMethod.POST)
    public Response sign(@RequestBody  @ApiParam(name = "sign",value = "签到数据",required = true) Sign sign){
        return signService.insertSign(sign);
    }
}
