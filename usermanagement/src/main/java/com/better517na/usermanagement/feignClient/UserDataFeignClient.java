package com.better517na.usermanagement.feignClient;

import com.better517na.usermanagement.model.*;
import com.better517na.usermanagement.model.Class;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static com.better517na.usermanagement.utils.Constant.RESPONSE_FALLBACK;

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
    @PostMapping("/sign/queryVSetBySetId")
    Response queryVSetBySetId(@RequestParam(value = "setID")String setID);
    @PostMapping("/student/queryClass")
    Response queryClassByClaID(@RequestParam(value = "claID")String claID);
    @PostMapping("/student/changePassword")
    Response changePassword(@RequestParam(value = "phone")String phone, @RequestParam(value = "password")String password);
    @PostMapping("/teacher/select")
    Response selectTeacher(@RequestParam(value = "phone")String phone);
    @PostMapping("/student/queryStudents")
    Response queryStudentsByClaID(@RequestParam(value = "claID")String claID);
    @PostMapping("/student/queryClassByStuNumber")
    Response queryClassByStuNumber(@RequestParam(value = "stuNumber")String stuNumber);
    @PostMapping("/student/remove")
    Response removeStudent(@RequestParam(value = "stuNumber")String stuNumber);
    @PostMapping("/student/addStudent")
    Response addStudent(@RequestParam(value = "stuNumber")String stuNumber, @RequestParam(value = "claID")String claID);
    @PostMapping("/teacher/creatClass")
    Response creatClass(@RequestBody Class aClass);
    @PostMapping("/teacher/changeClass")
    Response changeClass(@RequestBody Class cla);
    @PostMapping("/teacher/getClassN")
    Response getClassNByTeaNumber(@RequestParam(value = "teaNumber")String teaNumber);
    @PostMapping("/teacher/creatSet")
    Response creatSet(@RequestBody VSet set);
    @PostMapping("/teacher/getVSets")
    Response getVSetsByTeaNumber(@RequestParam(value = "teaNumber")String teaNumber);
    @PostMapping("/sign/getRecords")
    Response getRecordsBySetId(@RequestParam(value = "setId")String setId);
    @PostMapping("/sign/stuGetRecords")
    Response getRecordsByStuNumber(@RequestParam(value = "stuNumber")String stuNumber);
    @PostMapping("/student/queryClassByDynamic")
    Response queryClassByDynamic(@RequestParam(value = "dynamic")String dynamic);
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

            @Override
            public Response queryVSetBySetId(String setID) {
                return getFallback(throwable);
            }

            @Override
            public Response queryClassByClaID(String claID) {
                return getFallback(throwable);
            }

            @Override
            public Response changePassword(String phone, String password) {
                return getFallback(throwable);
            }

            @Override
            public Response selectTeacher(String phone) {
                return getFallback(throwable);
            }

            @Override
            public Response queryStudentsByClaID(String claID) {
                return getFallback(throwable);
            }

            @Override
            public Response queryClassByStuNumber(String stuNumber) {
                return getFallback(throwable);
            }

            @Override
            public Response removeStudent(String stuNumber) {
                return getFallback(throwable);
            }

            @Override
            public Response addStudent(String stuNumber, String claID) {
                return getFallback(throwable);
            }

            @Override
            public Response creatClass(Class aClass) {
                return getFallback(throwable);
            }

            @Override
            public Response changeClass(Class cla) {
                return getFallback(throwable);
            }

            @Override
            public Response getClassNByTeaNumber(String teaNumber) {
                return getFallback(throwable);
            }

            @Override
            public Response creatSet(VSet set) {
                return getFallback(throwable);
            }

            @Override
            public Response getVSetsByTeaNumber(String teaNumber) {
                return getFallback(throwable);
            }

            @Override
            public Response getRecordsBySetId(String setId) {
                return getFallback(throwable);
            }

            @Override
            public Response getRecordsByStuNumber(String stuNumber) {
                return getFallback(throwable);
            }

            @Override
            public Response queryClassByDynamic(String dynamic) {
                return getFallback(throwable);
            }

        };
    }
//封装方法，回调信息
    private Response getFallback(Throwable throwable){
        Response response = new Response<>();
        response.setStatus(RESPONSE_FALLBACK);
        response.setData(throwable.getMessage());
        response.setMsg("Feign回调");
        return response;
    }
}
