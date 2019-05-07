package com.better517na.usermanagement.service.impl;

import com.better517na.usermanagement.business.ITeacherBusiness;
import com.better517na.usermanagement.model.*;
import com.better517na.usermanagement.model.Class;
import com.better517na.usermanagement.service.ISMSService;
import com.better517na.usermanagement.service.ITeacherService;
import com.better517na.usermanagement.utils.IDUtil;
import com.better517na.usermanagement.utils.TimeUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

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
/*        response = smsService.verifySMSCode(account, code);
        if(response.getStatus() != 200){
            return response;
        }*/
        //2.根据手机号查询老师信息
        response = teacherBusiness.loginTeacher(account);
        if(response.getStatus() == 200){
            response.setMsg("登录成功");
        }else {
            response.setMsg("登录失败");
        }
        return response;
    }

    @Override
    public Response creatClass(Teacher teacher) {
        //判断老师是否已经创建班级了
        Response response = new Response();
        if(teacher.getaClass() != null){
            if(teacher.getaClass().getClaID() != null){
                response.setStatus(RESPONSE_FALSE);
                response.setMsg("已经创建了班级");
                return response;
            }
        }else {
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("创建班级信息错误");
            return response;
        }
        //没有创建班级，封装数据
        Class aClass = new Class();
        aClass.setClaID(IDUtil.getClaID(teacher.getTeaNumber()));
        aClass.setClaName(teacher.getaClass().getClaName());
        aClass.setCreaterID(teacher.getTeaNumber());
        aClass.setCreateTime(TimeUtil.getTime());
        aClass.setDynamic(this.getDynamicCode());
        response = teacherBusiness.creatClass(aClass);
        return response;
    }

    @Override
    public Response changeClass(Class cla) {
        Response response = new Response();
        if(cla == null){
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("入参错误");
            return response;
        }
        //重新生成动态码
        cla.setDynamic(this.getDynamicCode());
        response = teacherBusiness.changeClass(cla);
        return response;
    }

    @Override
    public Response getClassNByTeaNumber(String teaNumber) {
        Response response = new Response();
        if(StringUtils.isEmpty(teaNumber)){
            response.setStatus(RESPONSE_FALSE);
            return response;
        }
        response = teacherBusiness.getClassNByTeaNumber(teaNumber);
        return response;
    }

    @Override
    public Response creatSet(VSet set) {
        Response response = new Response();
        if(set == null){
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("入参错误");
            return response;
        }
        //封装数据
        set.setSetID(IDUtil.getSetID());
        response = teacherBusiness.creatSet(set);
        return response;
    }

    @Override
    public Response getVSetsByTeaNumber(String teaNumber) {
        Response response = new Response();
        if(StringUtils.isEmpty(teaNumber)){
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("入参错误");
            return response;
        }
        return teacherBusiness.getVSetsByTeaNumber(teaNumber);
    }

    private String getDynamicCode(){
        Random random = new Random();
        int i = random.nextInt(999999);
        i = i + 100000;
        return ""+i;
    }
}
