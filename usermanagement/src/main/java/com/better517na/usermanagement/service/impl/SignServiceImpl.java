package com.better517na.usermanagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.better517na.usermanagement.business.ISignBusiness;
import com.better517na.usermanagement.business.IStudentBusiness;
import com.better517na.usermanagement.model.LogProducer;
import com.better517na.usermanagement.model.Response;
import com.better517na.usermanagement.model.Sign;
import com.better517na.usermanagement.model.Student;
import com.better517na.usermanagement.service.ISignService;
import com.better517na.usermanagement.service.IStudentService;
import static com.better517na.usermanagement.utils.Constant.RESPONSE_FALSE;
import com.better517na.usermanagement.utils.IDUtil;
import com.better517na.usermanagement.utils.TimeUtil;
import com.google.gson.Gson;

@Service
public class SignServiceImpl implements ISignService {
    @Autowired
    private ISignBusiness signBusiness;

    @Autowired
    private LogProducer logProducer;

    @Override
    public Response signRecord(String id, String time) {
        if(id==null||"".equals(id)||time==null||"".equals(time)){
            Response response = new Response();
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("查询签到记录失败，入参错误!");
            return response;
        }
        return signBusiness.signRecord(id,time);
    }

    @Override
    public Response selectAllVSet(String claID) {
        if(null==claID||"".equals(claID)){
            Response response = new Response();
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("查询可签到失败，入参错误!");
            return response;
        }
        return signBusiness.selectAllVSet(claID);
    }

    @Override
    public Response insertSign(Sign sign) {
        if(sign == null){
            Response response = new Response();
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("签到失败，入参错误! "+new Gson().toJson(sign));
            return response;
        }
        if(sign.getSigId()==null){
            sign.setSigId(IDUtil.getSignID());
        }
        sign.setSigTime(TimeUtil.getTime());
        return signBusiness.insertSign(sign);
    }

    @Override
    public Response selectVSet(String claID, String stuId) {
        Response response = new Response();
        if(claID==null||"".equals(claID)){
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("班级编号不能为空! ");
            return response;
        }
        if(stuId==null||"".equals(stuId)){
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("学号不能为空! ");
            return response;
        }
        return signBusiness.selectVSet(claID,stuId);
    }
}
