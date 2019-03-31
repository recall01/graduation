package com.better517na.usermanagement.feignClient;

import feign.Logger;
import feign.hystrix.FallbackFactory;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author zhuojiu
 * @date 2019-02-26 17:13
 */
@FeignClient(name = "USERDATASERVICE",fallbackFactory = TestFeignClientFallbackFactory.class)
public interface TestFeignClient {
    @PostMapping("/student/test")
    void test();
}
@Component
class TestFeignClientFallbackFactory implements FallbackFactory<TestFeignClient> {
    @Override
    public TestFeignClient create(Throwable throwable) {
        return new TestFeignClient(){
            @Override
            public void test() {
                System.out.println("test方法回调了---"+throwable);
            }
        };
    }
}
