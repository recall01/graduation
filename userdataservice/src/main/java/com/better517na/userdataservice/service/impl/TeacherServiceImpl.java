package com.better517na.userdataservice.service.impl;

import com.better517na.userdataservice.dao.ITeacherDao;
import com.better517na.userdataservice.model.Class;
import com.better517na.userdataservice.model.Response;
import com.better517na.userdataservice.model.Teacher;
import com.better517na.userdataservice.model.VO.ClassN;
import com.better517na.userdataservice.model.VO.ClassVO;
import com.better517na.userdataservice.model.VSet;
import com.better517na.userdataservice.service.ITeacherService;
import com.better517na.userdataservice.utils.TimeUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

import static com.better517na.userdataservice.utils.Constant.RESPONSE_FALSE;
import static com.better517na.userdataservice.utils.Constant.RESPONSE_SUCCESS;

/**
 * @author zhuojiu
 * @description:
 * @date 2019-02-27 08:58
 */
@Service
public class TeacherServiceImpl implements ITeacherService {
    @Resource
    private ITeacherDao teacherDao;


    @Override
    public Response selectTeacher(String phone) {
        Teacher teacher = teacherDao.selectTeacher(phone);
        Response response = new Response();
        if(teacher!=null){
            response.setStatus(RESPONSE_SUCCESS);
            response.setData(teacher);
            response.setMsg("查询成功");
        }else {
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("查询失败");
        }
        return response;
    }

    @Override
    public Response creatClass(Class aClass) {
        Response response = new Response();
        if(aClass == null){
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("创建班级信息为空");
            return response;
        }
        if(teacherDao.creatClass(aClass)){
            response.setStatus(RESPONSE_SUCCESS);
            response.setMsg("创建班级成功");
        }else {
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("创建班级失败");
        }
        return response;
    }

    @Override
    public Response changeClass(Class cla) {
        Response response = new Response();
        if(cla == null){
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("入参不能为空");
            return response;
        }
        if(teacherDao.changeClass(cla)){
            response.setStatus(RESPONSE_SUCCESS);
            response.setMsg("修改班级信息成功");
        }else {
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("修改班级信息失败");
        }
        return response;
    }

    @Override
    public Response getClassNByTeaNumber(String teaNumber) {
        Response response = new Response();
        if(StringUtils.isEmpty(teaNumber)){
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("入参错误");
            return response;
        }
        //1.获取class信息
        Class c = teacherDao.getClassByTeaNumber(teaNumber);
        if(c == null){
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("数据不存在");
            return response;
        }
        String claId = c.getClaID();
        //2.获取该班级一共多少个学生
        int count = teacherDao.getCountByClaId(claId);
        ClassN cn = new ClassN();
        BeanUtils.copyProperties(c,cn);
        cn.setNumber(count);
        response.setStatus(RESPONSE_SUCCESS);
        response.setData(cn);
        response.setMsg("获取班级信息成功");
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
        if(!teacherDao.creatSet(set)){
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("执行SQL失败");
            return response;
        }
        response.setStatus(RESPONSE_SUCCESS);
        response.setMsg("添加签到信息成功");
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
        List<VSet> vSets = teacherDao.getVSetsByTeaNumber(teaNumber);
        response.setStatus(RESPONSE_SUCCESS);
        response.setData(vSets);
        response.setMsg("获取签到设置信息成功");
        return response;
    }

    @Override
    public Response getTeacherByTeaId(String teaId) {
        Teacher teacher = teacherDao.getTeacherByTeaId(teaId);
        Response response = new Response();
        if(teacher!=null){
            response.setStatus(RESPONSE_SUCCESS);
            response.setData(teacher);
            response.setMsg("获取教师信息成功");
        }else {
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("获取教师信息失败");
        }
        return response;
    }

}
