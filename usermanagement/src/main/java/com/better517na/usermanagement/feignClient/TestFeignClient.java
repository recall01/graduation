package com.better517na.usermanagement.feignClient;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author zhuojiu
 * @date 2019-02-26 17:13
 */
@FeignClient("USERDATASERVICE")
public interface TestFeignClient {
    @PostMapping("/student/test")
    void test();
}
