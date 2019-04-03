package com.better517na.usermanagement.service.impl;

import com.better517na.usermanagement.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.better517na.usermanagement.business.IStudentBusiness;
import com.better517na.usermanagement.service.IStudentService;
import static com.better517na.usermanagement.utils.Constant.RESPONSE_FALSE;
import com.better517na.usermanagement.utils.IDUtil;
import com.better517na.usermanagement.utils.TimeUtil;
import com.google.gson.Gson;

@Service
public class StudentServiceImpl implements IStudentService {
    @Autowired
    private IStudentBusiness studentBusiness;

    @Autowired
    private LogProducer logProducer;


    @Override
    public Response registStudent(Student student) {
        Response response = new Response();
        //1.先根据claId判断该班级是否存在
        Response r = this.queryClassByClaID(student.getClaID());
        if(r.getStatus()!=200){
            return r;
        }
        //2.判断手机号是否被注册
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
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("注册失败,参数错误!");
            logProducer.sendLog("my-topic",new Gson().toJson(student));
            return response;
        }
    }

    @Override
    public Response loginStudent(String account,String password) {
        Response response = new Response();
        if(account!=null&&!"".equals(account)&&password!=null&&!"".equals(password)){
            //判断查询结果是否为空，为空返回查询失败
            response = studentBusiness.selectStudent(account, password);
            if(response.getData()==null){
                response.setStatus(RESPONSE_FALSE);
                response.setMsg("查询结果为空,检查账号密码是否正确。");
            }
            return response;
        }else {
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("账号或密码为空 account:"+account+"--password:"+password);
//        logProducer.sendLog("my-topic","account "+account+" password "+password);
            return response;
        }

    }

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

    @Override
    public Response queryClassByClaID(String claID) {
        Response response = new Response();
        if(claID == null||"".equals(claID)){
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("查询班级信息失败!班级编号为空");
        }else {
            response = studentBusiness.queryClassByClaID(claID);
        }
        return response;
    }

    @Override
    public Response changePassword(String phone, String password) {
        Response response = new Response();
        if(phone.length()!=11||"".equals(phone)||phone == null){
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("输入的手机号有误");
            return response;
        }
        if(password.length()<6||password.length()>20||"".equals(password)||password == null){
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("输入的密码有误");
            return response;
        }
        return studentBusiness.changePassword(phone,password);
    }

    @Override
    public Response queryStudentsByClaID(String claID) {
        Response response = new Response();
        if(claID == null||"".equals(claID)){
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("班级编号有误");
            return response;
        }
        return studentBusiness.queryStudentsByClaID(claID);
    }

    @Override
    public Response addStudent(String stuNumber,String claID) {
        //1.先查询学生是否有班级
        //2.若有班级,无法添加,若没有将该学生添加至本班级
        Response response = this.queryClassByStuNumber(stuNumber);
        if(response.getStatus() == 200 && response.getData() == null){
            //该学生没有班级,添加到本班级
                //判断claID的班级是否存在
            Response rp = this.queryClassByClaID(claID);
            if(rp.getStatus() == 200){
                response = studentBusiness.addStudent(stuNumber,claID);
            }else {
                response.setStatus(RESPONSE_FALSE);
                response.setMsg("该班级不存在");
            }
        }else {
            //该学生有班级或查询该学生是否有班级的接口出现异常
            response.setStatus(RESPONSE_FALSE);
            if(response.getData() != null){
                response.setMsg("该学生已有班级,无法将该学生添加到本班级");
            }else {
                response.setMsg("无法将该学生添加到本班级");
            }
        }
        return response;
    }

    @Override
    public Response queryClassByStuNumber(String stuNumber) {
        Response response = new Response();
        if(stuNumber == null||"".equals(stuNumber)){
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("学号有误");
            return response;
        }
        return studentBusiness.queryClassByStuNumber(stuNumber);
    }

    @Override
    public Response removeStudent(Student student) {
        Response response = new Response();
        if(student.getClaID() == null|| "0".equals(student.getClaID())){
            //学生没有班级,不需要移除
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("该学生没有班级,无法移除");
            return response;
        }else {
            return studentBusiness.removeStudent(student.getStuNumber());
        }
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
