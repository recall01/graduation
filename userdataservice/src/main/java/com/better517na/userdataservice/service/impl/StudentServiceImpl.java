package com.better517na.userdataservice.service.impl;

import javax.annotation.Resource;

import com.better517na.userdataservice.model.Class;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import com.better517na.userdataservice.dao.IStudentDao;
import com.better517na.userdataservice.model.Response;
import com.better517na.userdataservice.model.Student;
import com.better517na.userdataservice.service.IStudentService;

import java.beans.Transient;
import java.util.HashMap;
import java.util.Map;

import static com.better517na.userdataservice.utils.Constant.RESPONSE_FALSE;
import static com.better517na.userdataservice.utils.Constant.RESPONSE_SUCCESS;

/**
 * @author zhuojiu
 * @description:
 * @date 2019-02-27 08:58
 */
@Service
public class StudentServiceImpl implements IStudentService {
    @Resource
    IStudentDao studentDao;

    @Override
    public Response registStudent(Student student){
        Response response = new Response();
        try {
            if(studentDao.registStudent(student)){
                response.setStatus(RESPONSE_SUCCESS);
                response.setMsg("注册成功");
            }else {
                response.setStatus(RESPONSE_FALSE);
                response.setMsg("注册失败");
            }
        } catch (DuplicateKeyException e){
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("注册失败,入参关键信息唯一，已在数据库中存在了。");
        }catch (Exception e){
            e.printStackTrace();
            response.setStatus(RESPONSE_FALSE);
            response.setData(e.getMessage());
            response.setMsg("注册学生异常");
        }finally {
            return response;
        }
    }

    @Override
    public Response selectStudent(String account, String password) {
        Response response = new Response();
        try {
            Student student = studentDao.selectStudent(account,password);
            response.setStatus(RESPONSE_SUCCESS);
            response.setData(student);
        }catch (Exception e){
            e.printStackTrace();
            response.setStatus(RESPONSE_FALSE);
            response.setData(e.getMessage());
            response.setMsg("查询学生异常");
        }finally {
            return response;
        }

    }

    @Transient
    @Override
    public Response changeInfo(Student student) {
        //1.判断学生信息是否修改成功
        Response response = new Response();
        try {
            if(studentDao.changeInfo(student)){
                response.setStatus(RESPONSE_SUCCESS);
                //1.返回修改成功后的数据
                response = this.selectStudent(student.getStuNumber(), student.getStuPassword());
                response.setMsg("修改学生信息成功");
            }else {
                response.setStatus(RESPONSE_FALSE);
                response.setMsg("修改学生信息失败");
            }

        }catch (Exception e){
            e.printStackTrace();
            response.setStatus(RESPONSE_FALSE);
            response.setData(e.getMessage());
            response.setMsg("修改学生信息异常");
        }finally {
            return response;
        }
    }

    @Override
    public Response queryClassByClaID(String claID) {
        Response response = new Response();
        try {
            Class c = studentDao.queryClassByClaID(claID);
            if(c!=null){
                response.setStatus(RESPONSE_SUCCESS);
                response.setData(c);
                response.setMsg("查询班级信息成功");
            }else {
                response.setStatus(RESPONSE_FALSE);
                response.setMsg("暂无该班级信息");
            }
        }catch (Exception e){
            e.printStackTrace();
            response.setStatus(RESPONSE_FALSE);
            response.setData(e.getMessage());
            response.setMsg("查询班级信息异常");
        }finally {
            return response;
        }
    }

    @Override
    public Response changePassword(String phone, String password) {
        Response response = new Response();
        try {
            if(studentDao.changePassword(phone,password)){
                response.setStatus(RESPONSE_SUCCESS);
                response.setMsg("修改密码成功");
            }else {
                response.setStatus(RESPONSE_FALSE);
                response.setMsg("修改密码失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            response.setStatus(RESPONSE_FALSE);
            response.setData(e.getMessage());
            response.setMsg("修改密码异常");
        }finally {
            return response;
        }
    }
}
