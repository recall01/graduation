package com.better517na.usermanagement.controller;

import com.better517na.usermanagement.Annotation.SysLogger;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

import static com.better517na.usermanagement.utils.Constant.RESPONSE_FALSE;

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

    @SysLogger("registStudent")
    @ApiOperation(value = "注册学生账号接口",notes = "填写必要的注册参数才能成功注册")
    @RequestMapping(value = "/regist",method = RequestMethod.POST)
    public Response registStudent(@RequestBody @ApiParam(name = "student",value = "用户注册数据",required = true) Student student){
        return studentService.registStudent(student);
    }

    @SysLogger("loginStudent")
    @HystrixCommand(fallbackMethod = "loginFallback")
    @ApiOperation(value = "学生登录接口",notes = "填写正确的账号和密码才能成功登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account",value = "用户账号",required = true,dataType = "String"),
            @ApiImplicitParam(name = "password",value = "用户密码",required = true,dataType = "String")
    })
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Response loginStudent(@RequestParam String account, @RequestParam String password){
        return studentService.loginStudent(account,password);
    }
//返回修改后的学生信息
    @SysLogger("changeInfo")
    @HystrixCommand(fallbackMethod = "changeFallback")
    @ApiOperation(value = "修改学生信息接口",notes = "填写要修改的信息进行修改")
    @RequestMapping(value = "/change",method = RequestMethod.POST)
    public Response changeInfo(@RequestBody  @ApiParam(name = "student",value = "修改学生数据",required = true) Student student){
        return studentService.changeInfo(student);
    }

    @SysLogger("queryClassByClaID")
    @ApiOperation(value = "根据班级编号查询班级是否存在",notes = "填写必要的班级编号")
    @RequestMapping(value = "/queryClass",method = RequestMethod.GET)
    public Response queryClassByClaID(@RequestParam @ApiParam(name = "claID",value = "班级编号",required = true) String claID){
        return studentService.queryClassByClaID(claID);
    }

    @SysLogger("queryStudentsByClaID")
    @ApiOperation(value = "根据班级编号查询班级里所有的学生",notes = "填写必要的班级编号")
    @RequestMapping(value = "/queryStudents",method = RequestMethod.GET)
    public Response queryStudentsByClaID(@RequestParam @ApiParam(name = "claID",value = "班级编号",required = true) String claID){
        return studentService.queryStudentsByClaID(claID);
    }

    @SysLogger("changePassword")
    @HystrixCommand(fallbackMethod = "changePasswordFallback")
    @ApiOperation(value = "修改学生密码接口",notes = "填写要修改后的密码进行修改")
    @RequestMapping(value = "/changePassword",method = RequestMethod.POST)
    public Response changePassword(@RequestParam  @ApiParam(name = "phone",value = "手机号",required = true) String phone,@RequestParam  @ApiParam(name = "password",value = "修改后的密码",required = true) String password){
        return studentService.changePassword(phone,password);
    }

    @SysLogger("addStudent")
    @ApiOperation(value = "将学生添加到本班级",notes = "根据stuNumber将学生添加到本班级")
    @RequestMapping(value = "/addStudent",method = RequestMethod.POST)
    public Response addStudent(@RequestParam  @ApiParam(name = "stuNumber",value = "学号",required = true) String stuNumber,@RequestParam  @ApiParam(name = "claID",value = "班级编号",required = true) String claID){
        return studentService.addStudent(stuNumber,claID);
    }

    @SysLogger("removeStudent")
    @ApiOperation(value = "将学生从班级移除",notes = "根据student的model将学生从班级移除")
    @RequestMapping(value = "/remove",method = RequestMethod.POST)
    public Response removeStudent(@RequestBody @ApiParam(name = "student",value = "要移除学生的model",required = true) Student student){
        return studentService.removeStudent(student);
    }

    @SysLogger("queryClassByStuNumber")
    @ApiOperation(value = "查询学生所在的班级信息",notes = "根据stuNumber查询学生所在班级信息")
    @RequestMapping(value = "/queryClass",method = RequestMethod.POST)
    public Response queryClassByStuNumber(@RequestParam  @ApiParam(name = "stuNumber",value = "学号",required = true) String stuNumber){
        return studentService.queryClassByStuNumber(stuNumber);
    }



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
