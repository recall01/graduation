package com.better517na.userdataservice.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.better517na.userdataservice.model.Response;
import com.better517na.userdataservice.model.Student;
import com.better517na.userdataservice.service.IStudentService;

/**
 * @author zhuojiu
 * @description:
 * @date 2019-02-21 19:13
 */
@RestController
@RequestMapping(value = "/student")
public class StudentController {

    @Autowired
    IStudentService studentService;

    @PostMapping(value = "/regist")
    public Response registStudent(@RequestBody Student student) throws Exception{
        return studentService.registStudent(student);
    }
    @PostMapping(value = "/select")
    public Response selectStudent(@RequestParam String account, @RequestParam String password) throws Exception{
        return studentService.selectStudent(account,password);
    }
    @PostMapping(value = "/change")
    public Response changeInfo(@RequestBody Student student) throws Exception{
        return studentService.changeInfo(student);
    }
    @PostMapping(value = "/queryClass")
    public Response queryClassByClaID(@RequestParam String claID) throws Exception{
        return studentService.queryClassByClaID(claID);
    }

    @PostMapping(value = "/queryStudents")
    public Response queryStudentsByClaID(@RequestParam String claID) throws Exception{
        return studentService.queryStudentsByClaID(claID);
    }

    @PostMapping(value = "/changePassword")
    public Response changePassword(@RequestParam String phone,@RequestParam String password) throws Exception{
        return studentService.changePassword(phone,password);
    }

    @PostMapping(value = "/queryClassByStuNumber")
    public Response queryClassByStuNumber(@RequestParam String stuNumber) throws Exception{
        return studentService.queryClassByStuNumber(stuNumber);
    }

    @PostMapping(value = "/remove")
    public Response removeStudent(@RequestParam String stuNumber) throws Exception{
        return studentService.removeStudent(stuNumber);
    }

    @PostMapping(value = "/addStudent")
    public Response addStudent(@RequestParam String stuNumber,@RequestParam String claID) throws Exception{
        return studentService.addStudent(stuNumber,claID);
    }

    @PostMapping(value = "/test")
    public void test() throws Exception{
        System.out.println("---test执行啦---");
    }

    @PostMapping(value = "/queryClassByDynamic")
    public Response queryClassByDynamic(@RequestParam String dynamic) throws Exception{
        return studentService.queryClassByDynamic(dynamic);
    }
}
