package com.better517na.usermanagement.controller;

import com.better517na.usermanagement.Annotation.SysLogger;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.better517na.usermanagement.model.Response;
import com.better517na.usermanagement.model.Sign;
import com.better517na.usermanagement.model.Student;
import com.better517na.usermanagement.service.ISignService;
import com.better517na.usermanagement.service.IStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import static com.better517na.usermanagement.utils.Constant.RESPONSE_FALSE;

/**
 * @author zhuojiu
 * @description:
 * @date 2019-02-26 17:21
 */
@Api(tags = "签到操作接口")
@RestController
@RequestMapping(value = "/sign")
public class SignController {
    @Autowired
    private ISignService signService;

    @SysLogger("signRecord")
    @HystrixCommand(fallbackMethod = "signFallback")
    @ApiOperation(value = "查询签到记录接口",notes = "根据时间查询签到记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "用户账号",required = true,dataType = "String"),
            @ApiImplicitParam(name = "time",value = "查看签到记录时间",required = true,dataType = "String")
    })
    @RequestMapping(value = "record",method = RequestMethod.GET)
    public Response signRecord(@RequestParam String id, @RequestParam String time){
        return signService.signRecord(id,time);
    }
//超过签到时间，已经签到的不给予展示
    @SysLogger("queryVSet")
    @HystrixCommand(fallbackMethod = "queryFallback")
    @ApiOperation(value = "查询可签到的设置接口",notes = "根据班级编号查询可签到的设置")
    @RequestMapping(value = "queryVSet",method = RequestMethod.GET)
    public Response queryVSet(@RequestParam @ApiParam(name = "claID",value = "班级编号",required = true) String claID,@RequestParam @ApiParam(name = "stuID",value = "学生学号",required = true) String stuID){
        return signService.selectVSet(claID,stuID);
    }

    @SysLogger("queryVSetBySetId")
//    @HystrixCommand(fallbackMethod = "queryAllFallback")
    @ApiOperation(value = "根据设置编号查询设置信息接口",notes = "根据设置编号查询设置信息接口")
    @RequestMapping(value = "queryVSetBySetId",method = RequestMethod.GET)
    public Response queryVSetBySetId(@RequestParam @ApiParam(name = "setID",value = "设置编号",required = true) String setID){
        return signService.queryVSetBySetId(setID);
    }

    @SysLogger("queryAllVSet")
    @HystrixCommand(fallbackMethod = "queryAllFallback")
    @ApiOperation(value = "查询该班级所有可签到的设置接口",notes = "根据班级编号查询所有可签到的设置")
    @RequestMapping(value = "queryAllVSet",method = RequestMethod.GET)
    public Response queryAllVSet(@RequestParam @ApiParam(name = "claID",value = "班级编号",required = true) String claID){
        return signService.selectAllVSet(claID);
    }

    @SysLogger("sign")
//    @HystrixCommand(fallbackMethod = "sigFallback")
    @ApiOperation(value = "学生签到的接口",notes = "传递正确参数进行签到")
    @RequestMapping(value = "sign",method = RequestMethod.POST)
    public Response sign(@RequestBody  @ApiParam(name = "sign",value = "签到数据",required = true) Sign sign){
        return signService.insertSign(sign);
    }



    //回调函数
    private Response signFallback(String id, String time){
        //记录日志
        return getFallback();
    }
    private Response queryFallback(String claID,String stuId){
        //记录日志
        return getFallback();
    }
    private Response queryAllFallback(String claID){
        //记录日志
        return getFallback();
    }
    private Response sigFallback(Sign sign){
        //记录日志
        return getFallback();
    }
    private Response getFallback(){
        Response response = new Response<>();
        response.setStatus(RESPONSE_FALSE);
        response.setMsg("服务器请求忙，请稍后再试。");
        return response;
    }


}
