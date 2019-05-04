package com.better517na.usermanagement.service.impl;

import com.better517na.usermanagement.model.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.better517na.usermanagement.business.ISignBusiness;
import com.better517na.usermanagement.service.ISignService;
import static com.better517na.usermanagement.utils.Constant.RESPONSE_FALSE;
import com.better517na.usermanagement.utils.IDUtil;
import com.better517na.usermanagement.utils.TimeUtil;
import com.google.gson.Gson;

import java.util.Date;

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
        Response response = new Response();
        if(sign == null){
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("签到失败，入参错误! "+new Gson().toJson(sign));
            return response;
        }
        if(sign.getSigId()==null){
            sign.setSigId(IDUtil.getSignID());
        }
        sign.setSigTime(TimeUtil.getTime());
        //1.根据setId查询该次签到的x,y坐标，范围
        Response reset = this.queryVSetBySetId(sign.getSetId());
        if(reset == null||reset.getData()==null){
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("查询编号信息失败! "+new Gson().toJson(sign));
            return response;
        }
        String s = new Gson().toJson(reset.getData());
        VSet set = new Gson().fromJson(s,VSet.class);
        //2.判断签到地址是否在范围内
        double scope = Double.valueOf(set.getScope());
        double setLatitude = Double.valueOf(set.getLatitude())*10000;
        double signLatitude = Double.valueOf(sign.getLatitude())*10000;
        double setLongitude = Double.valueOf(set.getLongitude())*10000;
        double signLongitude = Double.valueOf(sign.getLongitude())*10000;
        if(Math.abs(setLatitude-signLatitude)<=scope&&Math.abs(setLongitude-signLongitude)<=scope){
            //3.判断是否在签到时间内
            try {
                Date startTime = TimeUtil.getTimeByString(set.getStartSigTime());
                Date endTime = TimeUtil.getTimeByString(set.getEndSigTime());
                Date nowTime = TimeUtil.getTimeByString(TimeUtil.getTime());
                if(nowTime.getTime()>=startTime.getTime()&&nowTime.getTime()<=endTime.getTime()){
                    return signBusiness.insertSign(sign);
                }else {
                    response.setStatus(RESPONSE_FALSE);
                    response.setMsg("该时间暂时无法签到!");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("该地址暂时无法签到!");
        }
        return response;
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
        //根据结果，判断该时间是否在签到时间段内
        return signBusiness.selectVSet(claID,stuId);
    }

    @Override
    public Response queryVSetBySetId(String setID) {
        if(setID==null||"".equals(setID)){
            Response response = new Response();
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("设置编号不能为空! ");
            return response;
        }
        return signBusiness.queryVSetBySetId(setID);
    }

    @Override
    public Response getRecordsBySetId(String setId) {
        Response response = new Response();
        if(StringUtils.isEmpty(setId)){
            response.setStatus(RESPONSE_FALSE);
            response.setMsg("入参错误");
            return response;
        }
        return signBusiness.getRecordsBySetId(setId);
    }
}
