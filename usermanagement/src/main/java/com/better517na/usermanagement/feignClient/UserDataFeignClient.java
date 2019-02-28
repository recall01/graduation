package com.better517na.usermanagement.feignClient;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.better517na.usermanagement.model.Response;
import com.better517na.usermanagement.model.Sign;
import com.better517na.usermanagement.model.Student;

/**
 * @author zhuojiu
 * @description:
 * @date 2019-02-21 18:31
 */
@FeignClient(value = "USERDATASERVICE")
public interface UserDataFeignClient {
    @PostMapping("/student/regist")
    Response registStudent(@RequestBody Student student) throws Exception;
    @PostMapping("/student/select")
    Response selectStudent(@RequestParam(value = "account") String account, @RequestParam(value = "password") String password) throws Exception;
    @PostMapping("/student/change")
    Response changeInfo(@RequestBody Student student) throws Exception;

    @PostMapping("/sign/record")
    Response signRecord(@RequestParam(value = "id") String id, @RequestParam(value = "time") String time) throws Exception;
    @PostMapping("/sign/selectVSet")
    Response selectVSet(@RequestParam(value = "claID") String claID) throws Exception;
    @PostMapping("/sign/insertSign")
    Response insertSign(@RequestBody Sign sign) throws Exception;

}
