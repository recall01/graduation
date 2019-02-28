package com.better517na.userdataservice.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.better517na.userdataservice.dao.ISignDao;
import com.better517na.userdataservice.dao.IStudentDao;
import com.better517na.userdataservice.model.Response;
import com.better517na.userdataservice.model.SignRecord;
import com.better517na.userdataservice.model.Student;
import com.better517na.userdataservice.model.VSet;
import com.better517na.userdataservice.service.ISignService;
import com.better517na.userdataservice.service.IStudentService;
import static com.better517na.userdataservice.utils.Constant.RESPONSE_FALSE;
import static com.better517na.userdataservice.utils.Constant.RESPONSE_SUCCESS;

/**
 * @author zhuojiu
 * @description:
 * @date 2019-02-27 08:58
 */
@Service
public class SignServiceImpl implements ISignService {
    @Autowired
    ISignDao signDao;

    @Override
    public Response signRecord(String id, String time) {
        Response response = new Response();
        if(id==null||"".equals(id)||time==null||"".equals(time)){
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("查询签到信息失败，入参错误");
            return response;
        }
        Map m = new HashMap();
        m.put("id",id);
        m.put("time",time);
        try {
            List<SignRecord> signRecords = signDao.signRecord(m);
            response.setStatus(RESPONSE_SUCCESS);
            response.setData(signRecords);
            response.setMsg(""+signRecords.size());
        }catch (Exception e){
            e.printStackTrace();
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("查询签到信息异常 "+e.getMessage());
        }finally {
            return response;
        }
    }

    @Override
    public Response selectVSet(String claID) {
        Response response = new Response();
        if(null==claID||"".equals(claID)){
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("查询可签到失败，入参错误!");
            return response;
        }
        try {
            List<VSet> vSets = signDao.selectVSet(claID);
            response.setStatus(RESPONSE_SUCCESS);
            response.setData(vSets);
            response.setMsg(""+vSets.size());
        }catch (Exception e){
            e.printStackTrace();
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("查询可签到失败 "+e.getMessage());
        }finally {
            return response;
        }
    }
}