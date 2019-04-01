package com.better517na.usermanagement.controller;

import com.better517na.usermanagement.Annotation.SysLogger;
import com.better517na.usermanagement.feignClient.TestFeignClient;
import com.better517na.usermanagement.model.Response;
import com.better517na.usermanagement.service.ISMSService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhuojiu
 * @description:
 * @date 2019-02-26 17:11
 */
@Api(tags = "短信操作接口")
@RestController
@RequestMapping(value = "/sms")
public class SMSController {
    @Autowired
    private ISMSService sMSService;

    @SysLogger("verifySMSCode")
    @ApiOperation(value = "短信验证码验证接口",notes = "填写验证码")
    @RequestMapping(value = "verifySMSCode",method = RequestMethod.GET)
    public Response verifySMSCode(@RequestParam @ApiParam(name = "phone",value = "手机号",required = true) String phone, @RequestParam  @ApiParam(name = "code",value = "验证码",required = true) String code){

        return sMSService.verifySMSCode(phone,code);
    }
}
