package com.better517na.usermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.better517na.usermanagement.model.Response;
import com.better517na.usermanagement.model.Student;
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
@Api(tags = "学生操作接口")
@RestController
@RequestMapping(value = "/student")
public class StudentController {
    @Autowired
    IStudentService studentService;

    @ApiOperation(value = "注册学生账号接口",notes = "填写必要的注册参数才能成功注册")
    @RequestMapping(value = "/regist",method = RequestMethod.POST)
    public Response registStudent(@RequestBody @ApiParam(name = "student",value = "用户注册数据",required = true) Student student){
        return studentService.registStudent(student);
    }

    @ApiOperation(value = "学生登录接口",notes = "填写正确的账号和密码才能成功登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account",value = "用户账号",required = true,dataType = "String"),
            @ApiImplicitParam(name = "password",value = "用户密码",required = true,dataType = "String")
    })
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Response loginStudent(@RequestParam String account, @RequestParam String password){
        return studentService.loginStudent(account,password);
    }

    @ApiOperation(value = "修改学生信息接口",notes = "填写要修改的信息进行修改")
    @RequestMapping(value = "change",method = RequestMethod.POST)
    public Response changeInfo(@RequestBody  @ApiParam(name = "student",value = "修改学生数据",required = true) Student student){
        return studentService.changeInfo(student);
    }
}
