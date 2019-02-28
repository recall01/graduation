package com.better517na.userdataservice.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.better517na.userdataservice.model.Response;
import com.better517na.userdataservice.model.Student;
import com.better517na.userdataservice.service.ISignService;
import com.better517na.userdataservice.service.IStudentService;

/**
 * @author zhuojiu
 * @description:
 * @date 2019-02-21 19:13
 */
@RestController
@RequestMapping(value = "/sign")
public class SignController {

    @Resource
    ISignService signService;

    @PostMapping(value = "/record")
    public Response signRecord(@RequestParam String id, @RequestParam String time) throws Exception{
        return signService.signRecord(id,time);
    }
    @PostMapping(value = "/selectVSet")
    public Response selectVSet(@RequestParam String claID) throws Exception{
        return signService.selectVSet(claID);
    }

}
