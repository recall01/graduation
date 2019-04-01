package com.better517na.usermanagement.service.impl;

import com.better517na.usermanagement.business.IStudentBusiness;
import com.better517na.usermanagement.business.ITeacherBusiness;
import com.better517na.usermanagement.model.LogProducer;
import com.better517na.usermanagement.model.Response;
import com.better517na.usermanagement.model.Student;
import com.better517na.usermanagement.service.ISMSService;
import com.better517na.usermanagement.service.IStudentService;
import com.better517na.usermanagement.service.ITeacherService;
import com.better517na.usermanagement.utils.IDUtil;
import com.better517na.usermanagement.utils.TimeUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.better517na.usermanagement.utils.Constant.RESPONSE_FALSE;

@Service
public class TeacherServiceImpl implements ITeacherService {
    @Autowired
    private ITeacherBusiness teacherBusiness;
    @Autowired
    private ISMSService smsService;

    @Autowired
    private LogProducer logProducer;


    @Override
    public Response loginTeacher(String account, String code) {
        Response response = new Response();
        //1.先检验验证码是否正确
        if(account.length() != 11){
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("手机号错误");
            return response;
        }
        response = smsService.verifySMSCode(account, code);
        if(response.getStatus() != 200){
            return response;
        }
        //2.根据手机号查询老师信息
        response = teacherBusiness.loginTeacher(account);
        if(response.getStatus() == 200){
            response.setMsg("登录成功");
        }else {
            response.setMsg("登录失败");
        }
        return response;
    }
}
