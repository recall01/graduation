package com.better517na.usermanagement.controller;

import com.better517na.usermanagement.Annotation.SysLogger;
import com.better517na.usermanagement.model.Response;
import com.better517na.usermanagement.model.Student;
import com.better517na.usermanagement.service.IStudentService;
import com.better517na.usermanagement.service.ITeacherService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.better517na.usermanagement.utils.Constant.RESPONSE_FALSE;

/**
 * @author zhuojiu
 * @description:
 * @date 2019-02-26 17:21
 */
@Api(tags = "教师操作接口")
@RestController
@RequestMapping(value = "/teacher")
public class TeacherController {
    @Autowired
    private ITeacherService teacherService;

    //不提供教师注册接口
/*    @SysLogger("registTeacher")
    @HystrixCommand(fallbackMethod = "registFallback")
    @ApiOperation(value = "注册学生账号接口",notes = "填写必要的注册参数才能成功注册")
    @RequestMapping(value = "/regist",method = RequestMethod.POST)
    public Response registTeacher(@RequestBody @ApiParam(name = "student",value = "用户注册数据",required = true) Student student){
        return studentService.registStudent(student);
    }*/

    @SysLogger("loginTeacher")
    @HystrixCommand(fallbackMethod = "loginFallback")
    @ApiOperation(value = "教师登录接口",notes = "填写正确的手机号和验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone",value = "用户手机号",required = true,dataType = "String"),
            @ApiImplicitParam(name = "code",value = "验证码",required = true,dataType = "String")
    })
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Response loginTeacher(@RequestParam String phone, @RequestParam String code){
        return teacherService.loginTeacher(phone,code);
    }
//不提供教师信息修改接口
/*    @SysLogger("changeInfo")
    @HystrixCommand(fallbackMethod = "changeFallback")
    @ApiOperation(value = "修改学生信息接口",notes = "填写要修改的信息进行修改")
    @RequestMapping(value = "change",method = RequestMethod.POST)
    public Response changeInfo(@RequestBody  @ApiParam(name = "student",value = "修改学生数据",required = true) Student student){
        return studentService.changeInfo(student);
    }*/

    @SysLogger("queryClassByClaID")
    @ApiOperation(value = "根据班级编号查询班级是否存在",notes = "填写必要的班级编号")
    @RequestMapping(value = "/queryClass",method = RequestMethod.GET)
    public Response queryClassByClaID(@RequestParam @ApiParam(name = "claID",value = "班级编号",required = true) String claID){
       // return teacherService.queryClassByClaID(claID);
        return null;
    }
    //不提供教师密码修改接口
/*    @SysLogger("changePassword")
    @HystrixCommand(fallbackMethod = "changePasswordFallback")
    @ApiOperation(value = "修改学生密码接口",notes = "填写要修改后的密码进行修改")
    @RequestMapping(value = "changePassword",method = RequestMethod.POST)
    public Response changePassword(@RequestParam  @ApiParam(name = "phone",value = "手机号",required = true) String phone,@RequestParam  @ApiParam(name = "password",value = "修改后的密码",required = true) String password){
        return studentService.changePassword(phone,password);
    }*/

//回调函数
    private Response registFallback(Student student){
        return getFallback();
    }
    private Response loginFallback(String account, String password){
        return getFallback();
    }
    private Response changeFallback(Student student){
        return getFallback();
    }
    private Response changePasswordFallback(String phone,String password){
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
