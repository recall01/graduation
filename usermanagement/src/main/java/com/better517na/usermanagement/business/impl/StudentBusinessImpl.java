package com.better517na.usermanagement.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.better517na.usermanagement.business.IStudentBusiness;
import com.better517na.usermanagement.feignClient.UserDataFeignClient;
import com.better517na.usermanagement.model.LogProducer;
import com.better517na.usermanagement.model.Response;
import com.better517na.usermanagement.model.Student;
import static com.better517na.usermanagement.utils.Constant.RESPONSE_FALSE;

/**
 * @author zhuojiu
 * @description:
 * @date 2019-02-21 18:19
 */
@Component
public class StudentBusinessImpl implements IStudentBusiness {

    @Autowired
    private LogProducer logProducer;
    @Autowired
    UserDataFeignClient userDataFeignClient;

    @Override
    public Response registStudent(Student student) {
        try {
            return userDataFeignClient.registStudent(student);
        }catch (Exception e){
            e.printStackTrace();
            //记录日志
            Response response = new Response();
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("注册失败! "+e.getMessage());
//            logProducer.sendLog("my-topic",new Gson().toJson(student));
            return response;
        }
    }

    @Override
    public Response selectStudent(String account, String password) {
        try {
            return userDataFeignClient.selectStudent(account,password);
        }catch (Exception e){
            e.printStackTrace();
            //记录日志
            Response response = new Response();
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("查询学生信息失败! "+e.getMessage());
//            logProducer.sendLog("my-topic",new Gson().toJson(student));
            return response;
        }
    }

    @Override
    public Response changeInfo(Student student) {
        try {
            return userDataFeignClient.changeInfo(student);
        } catch (Exception e) {
            e.printStackTrace();
            //记录日志
            Response response = new Response();
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("查询签到记录失败! " + e.getMessage());
//            logProducer.sendLog("my-topic",new Gson().toJson(student));
            return response;
        }
    }

    @Override
    public Response queryClassByClaID(String claID) {
        try {
            return userDataFeignClient.queryClassByClaID(claID);
        } catch (Exception e) {
            e.printStackTrace();
            //记录日志
            Response response = new Response();
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("查询班级信息失败! " + e.getMessage());
//            logProducer.sendLog("my-topic",new Gson().toJson(student));
            return response;
        }
    }
}
