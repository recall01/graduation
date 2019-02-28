package com.better517na.usermanagement.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.better517na.usermanagement.business.ISignBusiness;
import com.better517na.usermanagement.business.IStudentBusiness;
import com.better517na.usermanagement.feignClient.UserDataFeignClient;
import com.better517na.usermanagement.model.LogProducer;
import com.better517na.usermanagement.model.Response;
import com.better517na.usermanagement.model.Student;
import com.better517na.usermanagement.service.ISignService;
import static com.better517na.usermanagement.utils.Constant.RESPONSE_FALSE;

/**
 * @author zhuojiu
 * @description:
 * @date 2019-02-21 18:19
 */
@Component
public class SignBusinessImpl implements ISignBusiness {

    @Autowired
    private LogProducer logProducer;
    @Autowired
    UserDataFeignClient userDataFeignClient;

    @Override
    public Response signRecord(String id, String time) {
        try {
            return userDataFeignClient.signRecord(id,time);
        }catch (Exception e){
            e.printStackTrace();
            //记录日志
            Response response = new Response();
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("修改学生信息失败! " + e.getMessage());
//            logProducer.sendLog("my-topic",new Gson().toJson(student));
            return response;
        }

    }

    @Override
    public Response selectVSet(String claID) {
        try {
            return userDataFeignClient.selectVSet(claID);
        }catch (Exception e){
            e.printStackTrace();
            //记录日志
            Response response = new Response();
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("查询可签到失败! " + e.getMessage());
//            logProducer.sendLog("my-topic",new Gson().toJson(student));
            return response;
        }
    }
}
