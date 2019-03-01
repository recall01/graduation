package com.better517na.usermanagement.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.better517na.usermanagement.business.IPermissionBusiness;
import com.better517na.usermanagement.business.ISignBusiness;
import com.better517na.usermanagement.feignClient.UserDataFeignClient;
import com.better517na.usermanagement.model.LogProducer;
import com.better517na.usermanagement.model.Response;
import com.better517na.usermanagement.model.Sign;
import static com.better517na.usermanagement.utils.Constant.RESPONSE_FALSE;

/**
 * @author zhuojiu
 * @description:
 * @date 2019-02-21 18:19
 */
@Component
public class PermissionBusinessImpl implements IPermissionBusiness {

    @Autowired
    private LogProducer logProducer;
    @Autowired
    UserDataFeignClient userDataFeignClient;

    @Override
    public Response queryAllPermissions() {
        try {
            return null;
            //return userDataFeignClient.insertSign(sign);
        }catch (Exception e){
            e.printStackTrace();
            //记录日志
            Response response = new Response();
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("签到异常! " + e.getMessage());
//            logProducer.sendLog("my-topic",new Gson().toJson(student));
            return response;
        }
    }
}
