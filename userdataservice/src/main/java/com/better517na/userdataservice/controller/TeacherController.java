package com.better517na.userdataservice.controller;

import com.better517na.userdataservice.model.Response;
import com.better517na.userdataservice.model.Student;
import com.better517na.userdataservice.service.IStudentService;
import com.better517na.userdataservice.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhuojiu
 * @description:
 * @date 2019-02-21 19:13
 */
@RestController
@RequestMapping(value = "/teacher")
public class TeacherController {

    @Autowired
    private ITeacherService teacherService;

    @PostMapping(value = "/select")
    public Response selectTeacher(@RequestParam String phone) throws Exception{
        return teacherService.selectTeacher(phone);
    }
}
