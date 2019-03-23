package com.better517na.usermanagement.feignClient;

import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.better517na.usermanagement.model.Response;
import com.better517na.usermanagement.model.Sign;
import com.better517na.usermanagement.model.Student;

import static com.better517na.usermanagement.utils.Constant.RESPONSE_FALSE;

/**
 * @author zhuojiu
 * @description:
 * @date 2019-02-21 18:31
 */
@FeignClient(value = "USERDATASERVICE",fallbackFactory = UserDataFeignClientFallbackFactory.class)
public interface UserDataFeignClient {
    @PostMapping("/student/regist")
    Response registStudent(@RequestBody Student student) throws Exception;
    @PostMapping("/student/select")
    Response selectStudent(@RequestParam(value = "account") String account, @RequestParam(value = "password") String password) throws Exception;
    @PostMapping("/student/change")
    Response changeInfo(@RequestBody Student student) throws Exception;

    @PostMapping("/sign/record")
    Response signRecord(@RequestParam(value = "id") String id, @RequestParam(value = "time") String time) throws Exception;
    @PostMapping("/sign/selectAllVSet")
    Response selectAllVSet(@RequestParam(value = "claID") String claID) throws Exception;
    @PostMapping("/sign/insertSign")
    Response insertSign(@RequestBody Sign sign) throws Exception;

    @PostMapping("/permission/queryAllPermissions")
    Response queryAllPermissions() throws Exception;
    @PostMapping("/sign/selectVSet")
    Response selectVSet(@RequestParam(value = "claID")String claID, @RequestParam(value = "stuId")String stuId) throws Exception;
}
@Component
class UserDataFeignClientFallbackFactory implements FallbackFactory<UserDataFeignClient>{

    @Override
    public UserDataFeignClient create(Throwable throwable) {

        return new UserDataFeignClient(){

            @Override
            public Response registStudent(Student student) throws Exception {
                return getFallback(throwable);
            }

            @Override
            public Response selectStudent(String account, String password) throws Exception {
                return getFallback(throwable);
            }

            @Override
            public Response changeInfo(Student student) throws Exception {
                return getFallback(throwable);
            }

            @Override
            public Response signRecord(String id, String time) throws Exception {
                return getFallback(throwable);
            }

            @Override
            public Response selectAllVSet(String claID) throws Exception {
                return getFallback(throwable);
            }

            @Override
            public Response insertSign(Sign sign) throws Exception {
                return getFallback(throwable);
            }

            @Override
            public Response queryAllPermissions() throws Exception {
                return getFallback(throwable);
            }

            @Override
            public Response selectVSet(String claID, String stuId) throws Exception {
                return getFallback(throwable);
            }
        };
    }
//封装方法，回调信息
    private Response getFallback(Throwable throwable){
        Response response = new Response<>();
        response.setStatus(RESPONSE_FALSE);
        response.setMsg("回调原因："+throwable);
        return response;
    }
}
