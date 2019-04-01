package com.better517na.userdataservice.service.impl;

import com.better517na.userdataservice.dao.IStudentDao;
import com.better517na.userdataservice.dao.ITeacherDao;
import com.better517na.userdataservice.model.Class;
import com.better517na.userdataservice.model.Response;
import com.better517na.userdataservice.model.Student;
import com.better517na.userdataservice.model.Teacher;
import com.better517na.userdataservice.service.IStudentService;
import com.better517na.userdataservice.service.ITeacherService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.Transient;

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
}
