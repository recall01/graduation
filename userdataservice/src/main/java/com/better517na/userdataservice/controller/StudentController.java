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

}
