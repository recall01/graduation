package com.better517na.usermanagement.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.better517na.usermanagement.business.IStudentBusiness;
import com.better517na.usermanagement.model.LogProducer;
import com.better517na.usermanagement.model.Response;
import com.better517na.usermanagement.model.Sign;
import com.better517na.usermanagement.model.Student;
import com.better517na.usermanagement.service.IStudentService;
import static com.better517na.usermanagement.utils.Constant.RESPONSE_FALSE;
import static com.better517na.usermanagement.utils.Constant.RESPONSE_SUCCESS;
import com.better517na.usermanagement.utils.IDUtil;
import com.better517na.usermanagement.utils.TimeUtil;
import com.google.gson.Gson;

@Service
public class StudentServiceImpl implements IStudentService {
    @Autowired
    private IStudentBusiness studentBusiness;

    @Autowired
    private LogProducer logProducer;


//    @HystrixCommand(fallbackMethod = "requestFallBack")
    @Override
    public Response registStudent(Student student) {
        student.setStuId(IDUtil.getStudentID());
        if(student.getStuMail()==null){
            student.setStuMail("");
        }
        if(student.getStuPhone()==null){
            student.setStuPhone("");
        }
        student.setPermissions("0");
        student.setRegisterTime(TimeUtil.getTime());
        if(this.checkStudent(student)){
            return studentBusiness.registStudent(student);
        }else {
            Response response = new Response();
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("注册失败,参数错误!");
            logProducer.sendLog("my-topic",new Gson().toJson(student));
            return response;
        }
    }
//    @HystrixCommand(fallbackMethod = "requestFallBack")
    @Override
    public Response loginStudent(String account,String password) {
        Response response = new Response();
        if(account!=null&&!"".equals(account)&&password!=null&&!"".equals(password)){
            return studentBusiness.selectStudent(account,password);
        }else {
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("账号或密码为空 account:"+account+"--password:"+password);
//        logProducer.sendLog("my-topic","account "+account+" password "+password);
            return response;
        }

    }
//    @HystrixCommand(fallbackMethod = "requestFallBack")
    @Override
    public Response changeInfo(Student student) {
        if("".equals(student.getStuNumber())||student.getStuNumber()==null){
            Response response = new Response();
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("修改学生信息失败!入参为空");
            return response;
        }
        return studentBusiness.changeInfo(student);
    }

    public Response requestFallBack(Student student) {
        Response response = new Response();
        response.setStatus(RESPONSE_FALSE);
        response.setMsg("请求数巨大，服务器无法响应，请稍后再试!");
        return response;
    }

    private boolean checkStudent(Student student){
        if(student==null){
            return false;
        }
        if(student.getStuName()==null||"".equals(student.getStuName())||(student.getStuName().length())>8){
            return false;
        }
        if(student.getStuNumber()==null||"".equals(student.getStuNumber())||(student.getStuNumber().length())<4){
            return false;
        }
        if(student.getStuPassword()==null||"".equals(student.getStuPassword())||(student.getStuPassword().length())<6){
            return false;
        }
        if(student.getStuPhone()==null||"".equals(student.getStuPhone())||(student.getStuPhone().length())!=11){
            return false;
        }return true;
    }
}
