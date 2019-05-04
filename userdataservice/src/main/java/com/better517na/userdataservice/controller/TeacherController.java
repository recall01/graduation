package com.better517na.userdataservice.controller;

import com.better517na.userdataservice.model.Class;
import com.better517na.userdataservice.model.Response;
import com.better517na.userdataservice.model.Student;
import com.better517na.userdataservice.model.VSet;
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
    @PostMapping(value = "/creatClass")
    public Response creatClass(@RequestBody Class aClass) throws Exception{
        return teacherService.creatClass(aClass);
    }
    @PostMapping(value = "/changeClass")
    public Response changeClass(@RequestBody Class cla) throws Exception{
        return teacherService.changeClass(cla);
    }
    @PostMapping(value = "/getClassN")
    public Response getClassNByTeaNumber(@RequestParam String teaNumber) throws Exception{
        return teacherService.getClassNByTeaNumber(teaNumber);
    }
    @PostMapping(value = "/creatSet")
    public Response creatSet(@RequestBody VSet set) throws Exception{
        return teacherService.creatSet(set);
    }
    @PostMapping(value = "/getVSets")
    public Response getVSetsByTeaNumber(@RequestParam String teaNumber) throws Exception{
        return teacherService.getVSetsByTeaNumber(teaNumber);
    }


}
