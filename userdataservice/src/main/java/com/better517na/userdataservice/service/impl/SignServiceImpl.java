package com.better517na.userdataservice.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.better517na.userdataservice.dao.ISignDao;
import com.better517na.userdataservice.dao.IStudentDao;
import com.better517na.userdataservice.model.Response;
import com.better517na.userdataservice.model.Sign;
import com.better517na.userdataservice.model.SignRecord;
import com.better517na.userdataservice.model.Student;
import com.better517na.userdataservice.model.VSet;
import com.better517na.userdataservice.service.ISignService;
import com.better517na.userdataservice.service.IStudentService;
import static com.better517na.userdataservice.utils.Constant.RESPONSE_FALSE;
import static com.better517na.userdataservice.utils.Constant.RESPONSE_SUCCESS;
import com.better517na.userdataservice.utils.TimeUtil;

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
    public Response selectAllVSet(String claID) {
        Response response = new Response();
        if(null==claID||"".equals(claID)){
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("查询可签到失败，入参错误!");
            return response;
        }
        try {
            List<VSet> vSets = signDao.selectAllVSet(claID);
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

    @Override
    public Response insertSign(Sign sign) {
        Response response = new Response();
        if(null==sign){
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("签到失败，入参错误!");
            return response;
        }
        try {
            if(signDao.insertSign(sign)){
                response.setStatus(RESPONSE_SUCCESS);
                response.setMsg("签到成功!");
            }else {
                response.setStatus(RESPONSE_FALSE);
                response.setMsg("签到失败!");
            }
        }catch (Exception e){
            e.printStackTrace();
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("签到异常! "+e.getMessage());
        }finally {
            return response;
        }
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
        Map map = new HashMap();
        try {
            map.put("claID",claID);
            map.put("stuId",stuId);
            List<VSet> vSets = signDao.selectVSet(map);
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

    @Override
    public Response queryVSetBySetId(String setID) {
        Response response = new Response();
        if(setID==null||"".equals(setID)){
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("设置编号不能为空! ");
            return response;
        }
        try {
            VSet set = signDao.queryVSetBySetId(setID);
            response.setStatus(RESPONSE_SUCCESS);
            response.setData(set);
        }catch (Exception e){
            e.printStackTrace();
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("查询设置信息失败 "+e.getMessage());
        }finally {
            return response;
        }
    }

    @Override
    public Response getRecordsBySetId(String setId) {
        Response response = new Response();
        if(StringUtils.isEmpty(setId)){
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("入参错误");
            return response;
        }
        List<SignRecord> records = signDao.getRecordsBySetId(setId);
        response.setStatus(RESPONSE_SUCCESS);
        response.setData(records);
        response.setMsg("获取签到记录数据成功");
        return response;
    }

    @Override
    public Response getRecordsByStuNumber(String stuNumber) {
        Response response = new Response();
        if(StringUtils.isEmpty(stuNumber)){
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("入参错误");
            return response;
        }
        List<SignRecord> records = signDao.getRecordsByStuNumber(stuNumber);
        response.setStatus(RESPONSE_SUCCESS);
        response.setData(records);
        response.setMsg("获取签到记录数据成功");
        return response;
    }
}
